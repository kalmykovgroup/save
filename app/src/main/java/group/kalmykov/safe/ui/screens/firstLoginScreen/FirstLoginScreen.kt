package group.kalmykov.safe.ui.screens.firstLoginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.screens.firstLoginScreen.components.ButtonsNext
import group.kalmykov.safe.ui.screens.firstLoginScreen.components.WelcomeTextAnimation
import group.kalmykov.safe.viewModels.FirstLoginViewModel

@Composable
fun FirstLoginScreen(firstLoginViewModel: FirstLoginViewModel){
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

        Box(
            modifier = Modifier.fillMaxSize().background(brush).padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            WelcomeTextAnimation(firstLoginViewModel)
            ButtonsNext(firstLoginViewModel)

        }
    }
}