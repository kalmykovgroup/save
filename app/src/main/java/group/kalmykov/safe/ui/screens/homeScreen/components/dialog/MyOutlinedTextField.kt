package group.kalmykov.safe.ui.screens.homeScreen.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.components.ColorTransformation

@Composable
fun MyOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String?,
    modifier: Modifier = Modifier,
    multicolored: Boolean = false,
    iconButton: @Composable (() -> Unit)? = null,
) {


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if(multicolored) ColorTransformation(LocalContext.current) else VisualTransformation.None, // Используем кастомную трансформацию
        modifier = modifier.fillMaxWidth(),
        label = { if(label != null) Text(softWrap = false,
            overflow = TextOverflow.Ellipsis,
            text = label, color = colorResource(R.color.colorTextContainerColorCommonField)
        ) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.focusedContainerColorCommonField),
            unfocusedContainerColor = colorResource(R.color.unfocusedContainerColorCommonField),
            focusedTextColor = colorResource(R.color.colorTextContainerColorCommonField),
            unfocusedTextColor = colorResource(R.color.colorTextContainerColorCommonField),
            focusedBorderColor = colorResource(R.color.colorTextContainerColorCommonField),
            unfocusedBorderColor = colorResource(R.color.border_common_text_field_unfocused),
        ),
        shape = RoundedCornerShape(6.dp),
        trailingIcon = iconButton?.let {

            {
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    it()
                }
            }

        }

    )
}