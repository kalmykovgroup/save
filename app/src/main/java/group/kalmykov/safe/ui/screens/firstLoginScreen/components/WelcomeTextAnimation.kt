package group.kalmykov.safe.ui.screens.firstLoginScreen.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.components.MeasureTextSize
import group.kalmykov.safe.viewModels.FirstLoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun WelcomeTextAnimation(firstLoginViewModel: FirstLoginViewModel) {

    var isStartAnimate by remember { mutableStateOf(firstLoginViewModel.isLoadScreen) }
    var isMovedTextUpDown by remember { mutableStateOf(firstLoginViewModel.isLoadScreen) }

    LaunchedEffect(Unit) {
        launch {
            isStartAnimate = true

            delay(1000)
            isMovedTextUpDown = true
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth() ,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = isStartAnimate,
            enter = slideInHorizontally(
                initialOffsetX = { -1000 }, // Смещение за экран для эффекта появления
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -1000 }, // Смещение за экран при исчезновении
                animationSpec = tween(durationMillis = 1000)
            )
        ) {
            Box(
                modifier = Modifier.wrapContentWidth()
            ) {

                val offsetYUp by animateDpAsState(
                    targetValue = if (isMovedTextUpDown) (-60).dp else 0.dp,
                    animationSpec = tween(durationMillis = 600), label = ""
                )

                Text(
                    text = "<>",
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 50.sp),
                    modifier = Modifier.align(Alignment.Center).offset(y = offsetYUp)
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f, fill = false),
            contentAlignment = Alignment.Center
        ) {

            WelcomeText(firstLoginViewModel)
        }

        AnimatedVisibility(
            visible = isStartAnimate,
            enter = slideInHorizontally(
                initialOffsetX = { 1000 }, // Смещение за экран для эффекта появления
                animationSpec = tween(durationMillis = 1000)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { 1000 }, // Смещение за экран при исчезновении
                animationSpec = tween(durationMillis = 1000)
            )
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth(),
                contentAlignment  = Alignment.BottomEnd
            ) {
                Box(modifier = Modifier.wrapContentWidth().wrapContentHeight()){
                    val offsetYDown by animateDpAsState(
                        targetValue = if (isMovedTextUpDown) (40).dp else 0.dp,
                        animationSpec = tween(durationMillis = 600), label = ""
                    )

                    Text(
                        text = "</>",
                        color = Color.White,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 50.sp),
                        modifier = Modifier.align(Alignment.Center).offset(y = offsetYDown)

                    )
                }
            }
        }

    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun WelcomeText(firstLoginViewModel: FirstLoginViewModel) {
    var gradientOffset by remember { mutableFloatStateOf(if(!firstLoginViewModel.isLoadScreen) 0f else 2f) }

    var displayedText by remember { mutableStateOf(if(!firstLoginViewModel.isLoadScreen) "" else firstLoginViewModel.text) }

    var estimatedWidth by remember { mutableStateOf(0.dp) }

    var expanded by remember { mutableStateOf(firstLoginViewModel.isLoadScreen) }



    val brush = Brush.linearGradient(
        colors = listOf(
            colorResource(R.color.welcome_text_color_1),
            colorResource(R.color.welcome_text_color_2),
            colorResource(R.color.welcome_text_color_3),
            colorResource(R.color.welcome_text_color_4),
        ),
        start = Offset(0f, 0f),
        end = Offset(gradientOffset * 500f, gradientOffset * 300f)
    )

    val style = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        brush = brush,
        shadow = Shadow(
            color = Color.White,
            offset = Offset(6f, 2f),
            blurRadius = 10f
        )
    )

    MeasureTextSize( text = firstLoginViewModel.text, style = style, callBack = { width, _ -> estimatedWidth = width })


    LaunchedEffect(firstLoginViewModel.text) {
        if(firstLoginViewModel.isLoadScreen)  return@LaunchedEffect

        delay(1800)
        expanded = true
        firstLoginViewModel.text.forEach{char ->
            delay(100) // Задержка между символами (настройте для скорости печати)
            displayedText += char
        }

        animate(
            initialValue = 0f,
            targetValue = 2f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        ) { value, _ ->
            gradientOffset = value
        }
    }


    Box(
        modifier = Modifier
            .widthIn(max = estimatedWidth + 5.dp)
            .padding(end = 4.dp), contentAlignment = Alignment.Center

    ) {

        Text(
            modifier = Modifier.animateContentSize(),
            text = displayedText,
            textAlign = TextAlign.Center,
            style = style
        )
    }

}


