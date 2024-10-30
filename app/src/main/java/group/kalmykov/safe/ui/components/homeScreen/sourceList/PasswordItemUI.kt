package group.kalmykov.safe.ui.components.homeScreen.sourceList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.models.Password

@Composable
fun PasswordItemUI (password: Password, deletePassword : (Password) -> Unit){
    val passwordVisibility =  remember { mutableStateOf(false) }

    Row{
        Column {
            Row(modifier = Modifier.height(80.dp)){
                Box(modifier = Modifier.weight(1f)){
                    Column(modifier = Modifier.fillMaxSize()){

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clickable(onClick = {}),
                            contentAlignment = Alignment.CenterStart){
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically){

                                IconCopy()

                                password.key.let {
                                    Row(verticalAlignment = Alignment.CenterVertically){
                                        Text(text = "key: ",
                                            style = TextStyle(color = colorResource(R.color.key_value_text_color), fontSize = 14.sp),
                                            modifier = Modifier
                                                .width(50.dp), textAlign = TextAlign.Start)
                                        Text(text = it)
                                    }
                                }

                            }
                        }

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clickable(onClick = {}),
                            contentAlignment = Alignment.CenterStart){

                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.fillMaxSize().weight(1f), verticalAlignment = Alignment.CenterVertically){
                                    IconCopy()

                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                        .padding(0.dp, 0.dp, 5.dp, 0.dp)){
                                        Text(text = "value: ",
                                            style = TextStyle(color = colorResource(R.color.key_value_text_color), fontSize = 14.sp),
                                            modifier = Modifier
                                                .width(50.dp)
                                            , textAlign = TextAlign.Start)

                                        PassValueBasicTextFieldBlock(password, passwordVisibility)
                                    }
                                }

                                BtnShow(passwordVisibility)
                            }
                        }
                    }
                }

                BtnDelete{deletePassword(password)}

            }
            HorizontalDivider(thickness = 1.dp, color = colorResource(R.color.password_divider))
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassValueBasicTextFieldBlock(password: Password, passwordVisibility : MutableState<Boolean>){


    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = password.value,
        singleLine = true,
        interactionSource = interactionSource,
        readOnly = true,
        cursorBrush = Brush.verticalGradient(
            colors = listOf(Color.Blue, Color.Cyan, Color.Red, Color.Magenta)
        ),
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {password.value = it},
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth().onFocusChanged {
                if (!it.isFocused) {
                    password.value = password.value
                }
            },
    ) { innerTextField -> OutlinedTextFieldDefaults.DecorationBox(
        value = password.value,
        innerTextField = innerTextField,
        enabled = true,
        singleLine = true,
        contentPadding = PaddingValues(3.dp, 0.dp),
        interactionSource = interactionSource,
        visualTransformation = VisualTransformation.None,
        container = {
            OutlinedTextFieldDefaults.ContainerBox(
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.Transparent,

                    ),
                shape = RoundedCornerShape(0.dp),
                focusedBorderThickness = 1.dp,
                unfocusedBorderThickness = 0.dp,
            )
        }
    )
    }
}

@Composable
fun IconCopy(){
    Box(modifier = Modifier
        .width(30.dp)
        .padding(5.dp)
        , contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.copy),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(colorResource(R.color.icon_copy))
        )
    }
}


@Composable
fun BtnShow(passwordVisibility : MutableState<Boolean>){

    var thumbIcon by remember {
        mutableIntStateOf(if (passwordVisibility.value) R.drawable.eye_visible else R.drawable.eye_hide)
    }

    Box(modifier = Modifier
        .width(40.dp)
        .height(30.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .border(width = 1.dp, color = Color.LightGray, shape= RoundedCornerShape(5.dp))
        .clickable(onClick = {
            passwordVisibility.value = !passwordVisibility.value
            thumbIcon = if (passwordVisibility.value) R.drawable.eye_visible else R.drawable.eye_hide
        })
        , contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(thumbIcon),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(29.dp),
            colorFilter = ColorFilter.tint(colorResource(R.color.icon_eye)),
        )
    }
}


@Composable
fun BtnDelete(deletePassword: () -> Unit){
    Box(modifier = Modifier
        .padding(10.dp, 3.dp)
        .width(30.dp)
        .fillMaxHeight()
        .clip(RoundedCornerShape(5.dp))
        .background(color = colorResource(R.color.delete_background))
        .clickable(onClick = {deletePassword()})
        .padding(3.dp),
        contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.delete_icon),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(25.dp),
            colorFilter = ColorFilter.tint(colorResource(R.color.black))
        )
    }


}
