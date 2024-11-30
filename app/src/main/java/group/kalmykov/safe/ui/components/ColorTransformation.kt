package group.kalmykov.safe.ui.components

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import group.kalmykov.safe.R

class ColorTransformation(private val context: Context) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = buildAnnotatedString {
            text.forEach { char ->
                var color = Color(ContextCompat.getColor(context, R.color.dataContainerColorCommonField))

                if(char.isDigit()){
                    color = Color(ContextCompat.getColor(context, R.color.canvas_color_level_2))
                }else if(!char.isLetter()){
                    color = Color(ContextCompat.getColor(context, R.color.canvas_color_level_3))
                }else if(char.isUpperCase()){
                    color = Color(ContextCompat.getColor(context, R.color.canvas_color_level_1))
                }
                withStyle(style = SpanStyle(color = color, textDecoration = TextDecoration.None, fontSize = 17.sp)) {
                    append(char.toString())
                }
            }
        }

        return TransformedText(transformedText, OffsetMapping.Identity)
    }
}
