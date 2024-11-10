package group.kalmykov.safe.ui.components.homeScreen.listSources

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import group.kalmykov.safe.R
import group.kalmykov.safe.entity.Source

@Composable
fun SourceFormModal(cancel: () -> Unit, save: (Source) -> Unit, source : Source? = null){

    var host by remember { mutableStateOf(source?.host ?: "") }
    var username by remember { mutableStateOf(source?.username ?: "") }
    var password by remember { mutableStateOf(source?.password ?: "") }
    var description by remember { mutableStateOf(source?.description ?: "") }

    MyCustomDialog(
        onDismissRequest = {cancel()},
        content = {
            Column {

                AddSourceTextField(host, {host = it}, "Source")
                AddSourceTextField(username, {username = it}, "Username")
                AddSourceTextField(password, {password = it}, "Password")
                AddSourceTextField(description, {description = it}, "Description", Modifier.height(130.dp), {})

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

@Composable
fun MyCustomDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        // We are copying the passed properties
        // then setting usePlatformDefaultWidth to false
        properties = properties.let {
            DialogProperties(
                dismissOnBackPress = it.dismissOnBackPress,
                dismissOnClickOutside = it.dismissOnClickOutside,
                securePolicy = it.securePolicy,
                usePlatformDefaultWidth = false
            )
        },
        content = {
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = colorResource(R.color.backgroundModelAddSource))
                    .padding(5.dp, 15.dp),
                content = content
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSourceTextField(value: String, setValue: (String) -> Unit, label: String? = null, modifier: Modifier = Modifier, callBack: @Composable() ((MutableInteractionSource) -> Unit)? = null){

    val interactionSource = remember { MutableInteractionSource() }

    if(callBack != null) callBack(interactionSource)
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
            .fillMaxWidth()
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
                text = label, color = colorResource(R.color.colorTextContainerColorCommonField)
            ) },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
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
    }
}
