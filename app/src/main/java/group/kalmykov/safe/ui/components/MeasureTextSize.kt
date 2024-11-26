package group.kalmykov.safe.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp

@Composable
fun MeasureTextSize(text: String, style: TextStyle, callBack: (width: Dp, height: Dp) -> Unit){
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult: TextLayoutResult = textMeasurer.measure(text = text, style = style, overflow = TextOverflow.Clip )

    val textWidthInDp = with(LocalDensity.current) { textLayoutResult.size.width.toDp() }
    val textHeightInDp = with(LocalDensity.current) { textLayoutResult.size.height.toDp() }

    callBack(textWidthInDp, textHeightInDp)
}