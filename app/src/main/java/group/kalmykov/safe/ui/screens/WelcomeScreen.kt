package group.kalmykov.safe.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.ui.components.welcomeScreen.ButtonsNext
import group.kalmykov.safe.ui.components.welcomeScreen.WelcomeTextAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun WelcomeScreen(Skip: () -> Unit, Introduction: () -> Unit){
    AnimatedTextExample(Skip, Introduction)
}


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun AnimatedTextExample(Skip: () -> Unit, Introduction: () -> Unit) {

    val brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF553000),
            Color(0xFF341D00),
            Color(0xFF000000),
            Color(0xFF000000),
            Color(0xFF000000),
            Color(0xFF000B24),
            Color(0xFF001342),
        ),
        start = Offset(0f, 0f),
        end = Offset(4f * 300f, 10f * 300f)
    )
    
    Box(
        modifier = Modifier.fillMaxSize().background(brush),
        contentAlignment = Alignment.Center
    ) {
         //ButtonsNext()
        //WelcomeText()
        WelcomeTextAnimation("Hello world!")
        ButtonsNext(Skip, Introduction)
    }

}






