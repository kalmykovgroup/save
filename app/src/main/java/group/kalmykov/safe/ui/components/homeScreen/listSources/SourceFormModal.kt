package group.kalmykov.safe.ui.components.homeScreen.listSources

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.entity.Source
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

    LaunchedEffect(Unit) {
        launch {
            delay(60)
            isOpenDialogGeneratePassword = true
        }
    }


    MyCustomDialog(
        onDismissRequest = {cancel()},
        content = {
            Column {

                AddSourceTextField(value = host, setValue = {host = it}, label = "Source")
                AddSourceTextField(value = username, setValue = {username = it}, label = "Username")
                AddSourceTextField(value = password, setValue = {password = it}, label = "Password", iconButton = {
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
                })
                AddSourceTextField(value = description, setValue = {description = it}, label = "Description", modifier = Modifier.height(130.dp))

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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSourceTextField(
    value: String,
    setValue: (String) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier,
    iconButton:  (@Composable () -> Unit)? = null){

    val interactionSource = remember { MutableInteractionSource() }

        var occupiedButtonSpace by remember { mutableStateOf(0.dp) }
         val localDensity = LocalDensity.current


        BasicTextField(
            value = value,
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = Brush.verticalGradient(
                colors = listOf(
                    colorResource(R.color.colorTextContainerColorCommonField),
                    colorResource(R.color.colorTextContainerColorCommonField),
                    colorResource(R.color.colorTextContainerColorCommonField),
                    colorResource(R.color.colorTextContainerColorCommonField),
                )
            ),
            textStyle = TextStyle(color = colorResource(R.color.dataContainerColorCommonField), fontSize = 18.sp),
            onValueChange = { setValue(it) },
            modifier = modifier
                .padding(5.dp)
                .height(55.dp)
                .fillMaxWidth()
        ) { innerTextField ->

            Box(contentAlignment = Alignment.CenterEnd){

                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,

                    innerTextField = {
                        Box(modifier = Modifier.fillMaxWidth().padding( end = if(iconButton != null) occupiedButtonSpace else 0.dp)) { // Добавляем отступы
                            innerTextField()
                        }
                    },
                    enabled = false,
                    singleLine = true,
                    interactionSource = interactionSource,
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
                            interactionSource = interactionSource,
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
                    }
                )

                if(iconButton != null){
                    Box(modifier.fillMaxHeight().wrapContentWidth().onGloballyPositioned { coordinates ->
                        val width = with(localDensity) { coordinates.size.width.toDp() } - 15.dp
                        occupiedButtonSpace = if (width < 0.dp) 0.dp else width
                    }) {
                        iconButton()
                    }
                }
            }

        }

}
