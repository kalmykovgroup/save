package group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastomSlider(setCountChar: (Int) -> Unit){

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
