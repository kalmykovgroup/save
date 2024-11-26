package group.kalmykov.safe.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.components.Keyboard
import group.kalmykov.safe.viewModels.CreatePasswordViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreatePasswordScreen(createPasswordViewModel: CreatePasswordViewModel) {

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
    ){innerPadding ->



        Box(modifier = Modifier.fillMaxSize().background(brush).padding(innerPadding), contentAlignment = Alignment.BottomCenter){
            val password = remember{ mutableStateListOf<Int>() }
            val pinKodRepeat = remember{ mutableStateListOf<Int>() }
            var isRepeatPassword by remember { mutableStateOf(false) }


            Column(modifier = Modifier.padding(top = 10.dp, bottom = 40.dp), verticalArrangement = Arrangement.Center) {

                Column(modifier = Modifier.padding(10.dp).weight(2f)) {

                    Column(modifier = Modifier.padding(10.dp).weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,){
                        Text("Придумайте пин-код для входа:", color = Color.White)
                        Spacer(Modifier.height(15.dp))

                        FlowRow(modifier = Modifier.fillMaxWidth().heightIn(min = 30.dp), horizontalArrangement = Arrangement.Center) {
                            repeat(password.size){
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.pass_icon),
                                    contentDescription = "*",
                                    colorFilter = if(password.toList() == pinKodRepeat.toList()) ColorFilter.tint(
                                        colorResource(R.color.successPasswordsStars)
                                    ) else ColorFilter.tint(Color.White),
                                    modifier = Modifier.size(20.dp, 20.dp)
                                )
                            }
                        }
                    }

                    val animatedWeight by animateFloatAsState(
                        targetValue = if (isRepeatPassword) 1f else 0.1f, // Плавный переход веса
                        animationSpec = tween(durationMillis = 1000),
                        label = "" // Настройка скорости анимации
                    )


                    AnimatedVisibility(
                        modifier = Modifier.weight(animatedWeight),
                        visible = isRepeatPassword,
                        enter = fadeIn(animationSpec = tween(1000)) + scaleIn(
                            initialScale = 0.5f,
                            animationSpec = tween(1000)
                        ) + expandVertically(animationSpec = tween(durationMillis = 500)),
                        exit = fadeOut(animationSpec = tween(1000)) + scaleOut(
                            targetScale = 0.5f,
                            animationSpec = tween(1000)
                        ) + shrinkVertically(animationSpec = tween(durationMillis = 500))

                    ){
                        Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally){
                            Text("Повторите:", color = Color.White)
                            Spacer(Modifier.height(15.dp))
                            FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                repeat(pinKodRepeat.size){
                                    Image(
                                        imageVector = ImageVector.vectorResource(R.drawable.pass_icon),
                                        contentDescription = "*",
                                        colorFilter = if(password.toList() == pinKodRepeat.toList()) ColorFilter.tint(
                                            colorResource(R.color.successPasswordsStars)
                                        ) else ColorFilter.tint(colorResource(R.color.starsRepeatPassword)),
                                        modifier = Modifier.size(20.dp, 20.dp)
                                    )
                                }
                            }
                        }
                    }

                }

                Keyboard(
                    leftBox = null,
                    rightBox = {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .clickable { isRepeatPassword = false },
                            contentAlignment = Alignment.Center){
                            Text(text = "Back", color = Color.White)
                        }
                    },
                    isRightBoxActive = isRepeatPassword && pinKodRepeat.size == 0,
                    keyClick = {
                        if(isRepeatPassword && pinKodRepeat.size < 100)
                            pinKodRepeat += it

                        else if(!isRepeatPassword && password.size < 100)
                            password += it
                    },
                    keyDelClick = {
                        if(isRepeatPassword){
                            if(pinKodRepeat.isNotEmpty()) pinKodRepeat.removeAt(pinKodRepeat.size - 1)
                            else{
                                isRepeatPassword = false
                            }
                        }else{
                            if(password.isNotEmpty()) password.removeAt(password.size - 1)
                        }

                    },
                    keyDelColorFilter = ColorFilter.tint(Color.White))


                val modifier = Modifier.width(150.dp)
                    .height(50.dp)
                    .border(width = 1.dp, color = colorResource(R.color.w_btn_border), shape = RoundedCornerShape(5.dp))
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(color = colorResource(R.color.w_btn_container))

                val textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.w_btn_color)
                )

                Column(modifier = Modifier.height(100.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    AnimatedVisibility(
                        visible = !isRepeatPassword && password.size >= 4,
                        enter = fadeIn(animationSpec = tween(500)) + scaleIn(
                            initialScale = 0.5f,
                            animationSpec = tween(500)
                        ),
                        exit = fadeOut(animationSpec = tween(500)) + scaleOut(
                            targetScale = 0.5f,
                            animationSpec = tween(500)
                        )

                    ){
                        Box(modifier = modifier.clickable { if(password.size >= 4) isRepeatPassword = true }, contentAlignment = Alignment.Center
                        ){
                            Text(text = "Продолжить", style = textStyle)
                        }
                    }

                    AnimatedVisibility(
                        visible = isRepeatPassword && password.toList() == pinKodRepeat.toList(),
                        enter = fadeIn(animationSpec = tween(1000)) + scaleIn(
                            initialScale = 0.5f,
                            animationSpec = tween(1000)
                        ),
                        exit = fadeOut(animationSpec = tween(1000)) + scaleOut(
                            targetScale = 0.5f,
                            animationSpec = tween(1000)
                        )

                    ){
                        Box(modifier = modifier.clickable {
                            createPasswordViewModel.savePassword(password.joinToString(""))
                        }, contentAlignment = Alignment.Center
                        ){
                            Text(text = "Сохранить", style = textStyle)
                        }
                    }
                }

            }
        }


    }
}