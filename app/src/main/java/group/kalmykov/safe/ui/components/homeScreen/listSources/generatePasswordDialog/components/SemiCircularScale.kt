package group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.evaluatePassword.EvaluatePassword1
import group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.evaluatePassword.EvaluatePassword2
import group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.evaluatePassword.EvaluatePassword3
import kotlin.math.ln
import kotlin.math.log2
import kotlin.math.roundToInt


@SuppressLint("ResourceAsColor")
@Composable
fun SemiCircularScale(
    password: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    strokeWidth: Float = 20f
) {
    var progress by remember { mutableFloatStateOf(0.5f) }

    val context = LocalContext.current
   // progress = evaluatePasswordStrengthUltimate(password) fsqbnvzfawqtxvbffgjkps

    var entropy by remember { mutableFloatStateOf(EvaluatePassword3(password)) }

    entropy  = EvaluatePassword3(password)


    progress = (entropy / 200).coerceIn(0.0f, 1.0f)


    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        // Список цветов из ресурсов
        val colors = listOf(
             R.color.canvas_color_level_1,
             R.color.canvas_color_level_2,
             R.color.canvas_color_level_3
        )

        // Список цветов из ресурсов
        val colorResources = listOf(
            colorResource(id = colors[0]),
            colorResource(id = colors[1]),
            colorResource(id = colors[2])
        )


        val texts = listOf( "Уязвимый", "Слабый", "Надежный")

        // Текущее состояние цвета
        var currentColorIndex by remember { mutableStateOf(0) }

        currentColorIndex =
        if(progress < 0.33f) 0
        else if(progress < 0.66f) 1
        else 2

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val radius =
                if ((width / 2) > height) height - strokeWidth / 2 else (width / 2) - strokeWidth / 2

            val size = Size(radius * 2, radius * 2)
            val topLeft = Offset((width - radius * 2) / 2, 0f + strokeWidth / 2)
            val style = Stroke(width = strokeWidth, /*cap = StrokeCap.Round*/)

            val rectHeight = 150f

            // Фон шкалы
            drawArc(
                color = backgroundColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                size = size,
                topLeft = topLeft,
                style = style
            )

            // Градиентная шкала
            drawArc(
                color = colorResources[currentColorIndex],
                startAngle = 180f,
                sweepAngle = 180f * progress,
                useCenter = false,
                size = size,
                topLeft = topLeft,
                style = style
            )

            drawRoundRect(
                color = colorResources[currentColorIndex],
                size = Size(radius , rectHeight),
                topLeft = Offset(x = width / 2 - radius / 2, y = radius - strokeWidth / 2),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(
                    x = 50f,
                    y = 50f
                ) // Радиус скругления
            )


            drawIntoCanvas { canvas ->
                val textPaint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    textSize = 18.sp.toPx()
                    color = android.graphics.Color.WHITE
                    textAlign = android.graphics.Paint.Align.CENTER
                }

                val textY = radius - strokeWidth / 2 - (textPaint.descent() + textPaint.ascent()) / 2 + rectHeight / 2

                canvas.nativeCanvas.drawText(
                    texts[currentColorIndex],
                    width / 2,
                    textY,
                    textPaint
                )
            }

            drawIntoCanvas { canvas ->
                val textPaint = Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                    textSize = 18.sp.toPx()
                    color = ContextCompat.getColor(context, colors[currentColorIndex])
                    textAlign = android.graphics.Paint.Align.CENTER
                }

                val textY = radius / 2 + strokeWidth / 2 + (textPaint.descent() + textPaint.ascent()) / 2

                canvas.nativeCanvas.drawText(
                    "${entropy.roundToInt()}",
                    width / 2,
                    textY,
                    textPaint
                )


            }

        }
        Box(modifier = Modifier.fillMaxWidth()){

        }
    }
}




