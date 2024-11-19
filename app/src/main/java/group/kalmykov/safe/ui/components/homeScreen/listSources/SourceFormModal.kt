package group.kalmykov.safe.ui.components.homeScreen.listSources

import android.content.Context
import android.widget.ArrayAdapter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import group.kalmykov.safe.R
import group.kalmykov.safe.entity.Source
import group.kalmykov.safe.ui.components.common.Keyboard
import group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.GeneratePasswordDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SourceFormDialog(cancel: () -> Unit, save: (Source) -> Unit, source : Source? = null){

    var host by remember { mutableStateOf(source?.host ?: "") }
    var username by remember { mutableStateOf(source?.username ?: "") }
    var password by remember { mutableStateOf(source?.password ?: "") }
    var description by remember { mutableStateOf(source?.description ?: "") }

   var isOpenDialogGeneratePassword by remember { mutableStateOf(false) }

    if(isOpenDialogGeneratePassword){

        GeneratePasswordDialog({password = it}, { isOpenDialogGeneratePassword = false })
    }

    val textInputColors = arrayOf(
        colorResource(R.color.dataContainerColorCommonField),
        colorResource(R.color.canvas_color_level_1),
        colorResource(R.color.canvas_color_level_2),
        colorResource(R.color.canvas_color_level_3),
    )


    MyCustomDialog(
        onDismissRequest = {cancel()},
        content = {
            Column {

                val modifier = Modifier.height(70.dp)

                MyOutlinedTextField(value = host, onValueChange = {host = it}, label = "Source", textInputColors = textInputColors,
                    modifier = modifier)
                MyOutlinedTextField(value = username, onValueChange = {username = it}, label = "Username", textInputColors = textInputColors,
                    modifier = modifier)
                MyOutlinedTextField(value = password, onValueChange = {password = it}, label = "Password", textInputColors = textInputColors,
                    modifier = modifier){

                    Box(modifier = Modifier.fillMaxHeight().aspectRatio(1f).clickable {
                        isOpenDialogGeneratePassword = true
                    }, contentAlignment = Alignment.Center){
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.password_generate),
                            contentDescription = "pass",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.height(29.dp),
                            colorFilter = ColorFilter.tint(colorResource(R.color.password_generate)),
                        )
                    }

                }

                MyOutlinedTextField(value = description,
                    onValueChange = {description = it},
                    label = "Description",
                    textInputColors = textInputColors,
                    modifier = Modifier.height(130.dp)
                )

                Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.End){
                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick = {cancel()}
                    ) {
                        Text("Отменить", fontSize = 17.sp)
                    }

                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick = {

                            if(source != null){
                                source.host = host
                                source.username = username
                                source.password = password
                                source.description = description
                            }

                            val newSource : Source =
                                source
                                    ?: Source(
                                        host = host,
                                        username = username,
                                        password = password,
                                        description = description
                                    )


                            save(newSource); cancel();}
                    ) {
                        Text("Сохранить", fontSize = 17.sp)
                    }
                }
            }
        }
    )

}



class ColorTransformation(private val colors: Array<Color>) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = buildAnnotatedString {
            text.forEach { char ->
                var color = colors[0]

                if(char.isDigit()){
                    color = colors[2]
                }else if(!char.isLetter()){
                    color = colors[3]
                }else if(char.isUpperCase()){
                    color = colors[1]
                }
                withStyle(style = SpanStyle(color = color, textDecoration = TextDecoration.None, fontSize = 17.sp)) {
                    append(char.toString())
                }
            }
        }

        return TransformedText(transformedText, OffsetMapping.Identity)
    }
}

@Composable
fun MyOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String?,
    modifier: Modifier = Modifier,
    textInputColors: Array<Color>? = null,
    iconButton: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if(textInputColors != null) ColorTransformation(textInputColors) else VisualTransformation.None, // Используем кастомную трансформацию
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
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .border(width = 1.dp, color = colorResource(R.color.border_right_btn_common_text_field), shape = RoundedCornerShape(5.dp))
                ) {
                    it()
                }
            }
        }

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSourceTextField(
    value: String,
    setValue: (String) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier,
    textInputColors: Array<Color>? = null,
    iconButton:  (@Composable () -> Unit)? = null){

    val interactionSource = remember { MutableInteractionSource() }


        BasicTextField(
            value = value,
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = if(textInputColors != null) ColorTransformation(textInputColors) else VisualTransformation.None,
            cursorBrush = Brush.verticalGradient(
                colors = listOf(
                    colorResource(R.color.colorTextContainerColorCommonField),
                    colorResource(R.color.colorTextContainerColorCommonField),
                    colorResource(R.color.colorTextContainerColorCommonField),
                    colorResource(R.color.colorTextContainerColorCommonField),
                )
            ),
            textStyle = TextStyle(color = Color.White, textDecoration = TextDecoration.None),
            onValueChange = { setValue(it) },
            modifier = modifier
                .padding(5.dp)
                .height(55.dp)
                .fillMaxWidth(),

        ) { innerTextField ->


                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,

                    innerTextField = innerTextField,
                    enabled = false,
                    singleLine = true,
                    interactionSource = remember { MutableInteractionSource() },
                    visualTransformation = VisualTransformation.None,
                    label = { if(label != null) Text(softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        text = label, color = colorResource(R.color.colorTextContainerColorCommonField)
                    ) },
                    container = {
                        Container(
                            modifier = Modifier.width(IntrinsicSize.Max).fillMaxWidth(),
                            enabled = true,
                            isError = false,
                            interactionSource = remember { MutableInteractionSource() },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = colorResource(R.color.focusedContainerColorCommonField),
                                unfocusedContainerColor = colorResource(R.color.unfocusedContainerColorCommonField),
                                focusedTextColor = colorResource(R.color.colorTextContainerColorCommonField),
                                unfocusedTextColor = colorResource(R.color.colorTextContainerColorCommonField),
                                focusedBorderColor = colorResource(R.color.colorTextContainerColorCommonField),
                                unfocusedBorderColor = colorResource(R.color.border_common_text_field_unfocused),
                            ),
                            shape = RoundedCornerShape(6.dp),
                            focusedBorderThickness = 1.dp,
                            unfocusedBorderThickness = 1.dp,
                        )
                    },
                    trailingIcon = iconButton?.let {
                        {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .aspectRatio(1f)
                                    .wrapContentWidth()
                                    .border(width = 1.dp, color = Color.White)
                            ) {
                                it()
                            }
                        }
                    }
                )
        }

}
