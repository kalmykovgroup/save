package group.kalmykov.safe.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.components.common.Keyboard
import group.kalmykov.safe.viewModels.LoginViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(loginViewModel : LoginViewModel, showBiometrics: () -> Unit){

    val brush = Brush.linearGradient(
        colors = listOf(
            colorResource(R.color.background_brush_1),
            colorResource(R.color.background_brush_2),
            colorResource(R.color.background_brush_3),
            colorResource(R.color.background_brush_4),
            colorResource(R.color.background_brush_5),
            colorResource(R.color.background_brush_6),
            colorResource(R.color.background_brush_7),
        ),
        start = Offset(0f, 0f),
        end = Offset(4f * 300f, 10f * 300f)
    )


    Scaffold(
        topBar = {

        }
    ) { innerPadding ->

        val context = LocalContext.current

        val pinKod = remember { mutableStateListOf<Int>() }

        Box(
            modifier = Modifier.fillMaxSize().background(brush).padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column{
                Column(modifier = Modifier.padding(10.dp).weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,){
                    Text("Пин-код для входа:", color = Color.White)
                    Spacer(Modifier.height(15.dp))


                    FlowRow(modifier = Modifier.fillMaxWidth().heightIn(min = 30.dp), horizontalArrangement = Arrangement.Center) {
                        repeat(pinKod.size){
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.pass_icon),
                                contentDescription = "*",
                                colorFilter = ColorFilter.tint(
                                    if(loginViewModel.isSuccess == true) colorResource(R.color.pass_green_success)
                                    else if (loginViewModel.isSuccess == false) colorResource(R.color.pass_red_error)
                                    else Color.White
                                ),
                                modifier = Modifier.size(20.dp, 20.dp)
                            )
                        }
                    }
                }

                val supportsBiometrics : (@Composable () -> Unit)? = if(loginViewModel.supportsBiometrics) { { BtnBiometricShowLogin(showBiometrics) } } else null

                Keyboard(
                    leftBox = { BtnCantLogIn() },
                    rightBox = supportsBiometrics,
                    isRightBoxActive = pinKod.size == 0,
                    keyDelClick = { if(pinKod.isNotEmpty()) pinKod.removeAt(pinKod.size - 1) },
                    keyClick = { pinKod += it },
                    keyDelColorFilter = ColorFilter.tint(Color.White)
                )

                val modifier = Modifier.width(150.dp)
                    .height(50.dp)
                    .border(width = 1.dp, color = colorResource(R.color.w_btn_border), shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = colorResource(R.color.w_btn_container))

                val textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.w_btn_color))

                Column(modifier = Modifier.height(100.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    AnimatedVisibility(
                        visible = pinKod.size >= 4,
                        enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                            initialScale = 0.5f,
                            animationSpec = tween(300)
                        ),
                        exit = fadeOut(animationSpec = tween(300)) + scaleOut(
                            targetScale = 0.5f,
                            animationSpec = tween(300)
                        )

                    ){
                        Box(modifier = modifier.clickable {
                            loginViewModel.singIn(pinKod.joinToString(""), context)
                        }, contentAlignment = Alignment.Center
                        ){
                            Text(text = "Войти", style = textStyle)
                        }
                    }
                }
            }

        }
    }



}

@Composable
fun BtnCantLogIn(){
    Button(
        onClick = {  },
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent),
        contentPadding = PaddingValues()

    ){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Text(text = "Не могу", color = Color.White)
            Text(text = "войти", color = Color.White)
        }

    }
}

@Composable
fun BtnBiometricShowLogin(showBiometrics: () -> Unit){
    //Отпечаток пальца
    Button(
        onClick = { showBiometrics()  },
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent),
        contentPadding = PaddingValues()

    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.fingerprint),
            contentDescription = "Отпечаток",
            modifier = Modifier.size(50.dp, 50.dp).align(Alignment.CenterVertically),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}