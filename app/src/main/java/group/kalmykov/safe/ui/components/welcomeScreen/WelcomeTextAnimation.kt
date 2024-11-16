package group.kalmykov.safe.ui.components.welcomeScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun WelcomeTextAnimation(text: String, isLoadScreen: Boolean) {

    var isStartAnimate by remember { mutableStateOf(isLoadScreen) }
    var isMovedTextUpDown by remember { mutableStateOf(isLoadScreen) }

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

               WelcomeText(text, isLoadScreen)
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
fun WelcomeText(text: String, isLoadScreen: Boolean) {
    var gradientOffset by remember { mutableFloatStateOf(if(!isLoadScreen) 0f else 2f) }

    var displayedText by remember { mutableStateOf(if(!isLoadScreen) "" else text) }

    var estimatedWidth by remember { mutableStateOf(0.dp) }

    var expanded by remember { mutableStateOf(isLoadScreen) }



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

    MeasureTextWidth( text = text, style = style, callBack = { width -> estimatedWidth = width })


    LaunchedEffect(text) {
        if(isLoadScreen)  return@LaunchedEffect

        delay(1800)
        expanded = true
        text.forEach{char ->
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

@Composable
fun MeasureTextWidth(text: String, style: TextStyle, callBack: (Dp) -> Unit) {
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult: TextLayoutResult = textMeasurer.measure(text = text, style = style, overflow = TextOverflow.Clip )

    val textWidthInDp = with(LocalDensity.current) { textLayoutResult.size.width.toDp() }

    callBack(textWidthInDp)
   // val textHeightInDp = with(LocalDensity.current) { textLayoutResult.size.height.toDp() }
}


