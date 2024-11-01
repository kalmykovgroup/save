package group.kalmykov.safe.ui.components.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBasicTextField(value: String, setValue: (String) -> Unit, label: String? = null, callBack: @Composable() ((MutableInteractionSource) -> Unit)? = null){

    val interactionSource = remember { MutableInteractionSource() }

    if(callBack != null) callBack(interactionSource)

    BasicTextField(
        value = value,
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = Brush.verticalGradient(
            colors = listOf(Color.Blue, Color.Cyan, Color.Red, Color.Magenta)
        ),
        onValueChange = { setValue(it) },
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = false,
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            label = { if(label != null) Text(softWrap = false,
                overflow = TextOverflow.Ellipsis,
                text = label, color = Color.Black) },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.border_common_text_field_focused),
                        unfocusedBorderColor = colorResource(R.color.border_common_text_field_unfocused),

                        ),
                    shape = RoundedCornerShape(6.dp),
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp,
                )
            }
        )
    }
}