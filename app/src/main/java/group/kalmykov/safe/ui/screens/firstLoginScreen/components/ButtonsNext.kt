package group.kalmykov.safe.ui.screens.firstLoginScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.viewModels.FirstLoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ButtonsNext(firstLoginViewModel: FirstLoginViewModel){
    var isVisibleBtn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            delay(4300)
            isVisibleBtn = true
            firstLoginViewModel.isLoadScreen = true
        }
    }

    AnimatedVisibility(
        visible = isVisibleBtn || firstLoginViewModel.isLoadScreen,
        enter = fadeIn(animationSpec = tween(1000)) + scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(1000)
        ),
        exit = fadeOut(animationSpec = tween(1000)) + scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(1000)
        )

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 350.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val modifier = Modifier.width(200.dp)
                .height(50.dp)
                .border(width = 1.dp, color = colorResource(R.color.w_btn_border), shape = RoundedCornerShape(5.dp))
                .clip(shape = RoundedCornerShape(5.dp))
                .background(color = colorResource(R.color.w_btn_container))

            val textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold,
                color = colorResource(R.color.w_btn_color)
            )

            Box(modifier = modifier.clickable { firstLoginViewModel.navAbout() }, contentAlignment = Alignment.Center
            ){
                Text(text = "Ознакомиться", style = textStyle)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = modifier.clickable { firstLoginViewModel.navSkip() }, contentAlignment = Alignment.Center

            ){
                Text(text = "Пропустить", style = textStyle)
            }

        }

    }
}