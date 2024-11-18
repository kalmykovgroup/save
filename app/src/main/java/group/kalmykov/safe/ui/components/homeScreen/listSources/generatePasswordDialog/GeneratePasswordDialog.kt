package group.kalmykov.safe.ui.components.homeScreen.listSources

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import group.kalmykov.safe.R
import group.kalmykov.safe.entity.Source
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratePasswordDialog(save: (String) -> Unit, cancel: () -> Unit){
    MyCustomDialog(
        onDismissRequest = {cancel()},
        content = {
            Column {

               var progress by remember { mutableFloatStateOf(0.5f) }
               var countChar by remember { mutableIntStateOf(4) }

                Text(text = "Уровень защиты", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

               SemiCircularScale(
                   progress = progress,
                   modifier = Modifier
                       .height(150.dp),
                   backgroundColor = colorResource(R.color.canvasBackgroundGeneratePassword),
                   strokeWidth = 150f
               )

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "$countChar символов", color = Color.White)

                MySlider(setCountChar = { countChar = it })


                Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.End){
                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick = {cancel()}
                    ) {
                        Text("Отменить", fontSize = 17.sp)
                    }

                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick = { save("123"); cancel() }
                    ) {
                        Text("Ок", fontSize = 17.sp)
                    }
                }
            }
        }
    )
}



@SuppressLint("ResourceAsColor")
@Composable
fun SemiCircularScale(
    progress: Float, // Значение прогресса от 0.0 до 1.0
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    strokeWidth: Float = 20f
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        // Список цветов из ресурсов
        val colors = listOf(
            colorResource(id = R.color.canvas_color_level_1),
            colorResource(id = R.color.canvas_color_level_2),
            colorResource(id = R.color.canvas_color_level_3)
        )
        val texts = listOf( "Уязвимый", "Слабый", "Надежный")


        // Текущее состояние цвета
        var currentColorIndex by remember { mutableStateOf(0) }

        currentColorIndex = if(progress < 0.5f) 0
        else if(progress < 0.7f) 1
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
                color = colors[currentColorIndex],
                startAngle = 180f,
                sweepAngle = 180f * progress,
                useCenter = false,
                size = size,
                topLeft = topLeft,
                style = style
            )

            drawRoundRect(
                color = colors[currentColorIndex],
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

        }
        Box(modifier = Modifier.fillMaxWidth()){

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySlider(setCountChar: (Int) -> Unit){

    var sliderValue  by remember { mutableFloatStateOf(4f) }

    val valueRange = 4f..64f

    Slider(
        value = sliderValue ,
        onValueChange = { sliderValue = it;  setCountChar(sliderValue.roundToInt()) },
        valueRange = valueRange, // Диапазон значений
        steps = 0, // Количество шагов (64 - 4 - 1)
        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color.Red,
            activeTrackColor = Color.Yellow,
            inactiveTrackColor = Color.LightGray
        ),
        thumb = {
            // Кастомный держатель

            val slider_holder = colorResource(R.color.slider_holder)
            Canvas(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                drawCircle(color = slider_holder)
            }
        },
        track = { sliderPositions ->

            val colorActivePartTrack = colorResource(R.color.active_part_track)
            val colorNotActivePartTrack = colorResource(R.color.not_active_part_track)

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp) // Высота трека
            ) {
                val start = Offset(0f, center.y)
                val activeWidth = size.width * (sliderValue - valueRange.start) / (valueRange.endInclusive - valueRange.start)
                val activeEnd = Offset(activeWidth, center.y)
                val inactiveStart = activeEnd
                val end = Offset(size.width, center.y)

                // Активная часть трека
                drawLine(
                    color = colorActivePartTrack,
                    start = start,
                    end = activeEnd,
                    strokeWidth = 4.dp.toPx()
                )

                // Неактивная часть трека
                drawLine(
                    color = colorNotActivePartTrack,
                    start = inactiveStart,
                    end = end,
                    strokeWidth = 4.dp.toPx()
                )
            }
        }
    )
}
