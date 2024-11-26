package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.BtnCopy
import group.kalmykov.safe.ui.components.BtnPassVisible
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.InputContainer

@Composable
fun PasswordField(source: Source){

    var passwordVisibility by  remember { mutableStateOf(false) }

   /* InputContainer(label = "Password", inputField = {
        BasicTextField(
            value = source.password,
            singleLine = true,
            onValueChange = {},
            textStyle = TextStyle(color = colorResource(R.color.color_value_field_resource)),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }, attributes = {
        BtnPassVisible(passwordVisibility) { passwordVisibility = it }
        BtnCopy(value = source.password)
    })*/

    InputContainer(
        label = "Password",
        attributes = {
                BtnPassVisible(passwordVisibility) { passwordVisibility = it }
                BtnCopy(value = source.password)
            },
        text = source.password,
        visualText = passwordVisibility,
    )
}