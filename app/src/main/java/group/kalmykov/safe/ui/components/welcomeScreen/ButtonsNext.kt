package group.kalmykov.safe.ui.components.welcomeScreen

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import group.kalmykov.safe.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random



@Composable
fun ButtonsNext(Skip: () -> Unit, Introduction: () -> Unit) {
 var isVisibleBtn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        launch {
            delay(4300)
            isVisibleBtn = true

        }
    }

    AnimatedVisibility(
        visible = isVisibleBtn,
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
                color = colorResource(R.color.w_btn_color))

            Box(modifier = modifier.clickable { Introduction() }, contentAlignment = Alignment.Center
            ){
                Text(text = "Ознакомиться", style = textStyle)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = modifier.clickable { Skip() }, contentAlignment = Alignment.Center

            ){
                Text(text = "Пропустить", style = textStyle)
            }

        }

    }
}

