package group.kalmykov.safe.ui.components.homeScreen.sourceList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.models.Password
import group.kalmykov.safe.models.Source_
import group.kalmykov.safe.ui.components.common.CommonBasicTextField

class ListSourcesComponent: ViewModel(){

    var sources: MutableList<Source_> = mutableStateListOf(
        Source_("Source_1"),
        Source_("Source_2")
    )


    @Composable
    fun Show(){
        Box(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 75.dp)){
            LazyColumn(modifier = Modifier
                .background(color = colorResource(R.color.list_source_background))
                .padding(0.dp, 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)){
                items(sources){ source ->
                    SourceRow(source)
                }
            }
        }

    }



    @Composable
    fun SourceRow(source: Source_){
        var isOpenSource by remember { mutableStateOf(false) }

        var isOpenDialogAddPassword by remember { mutableStateOf(false) }

        if(isOpenDialogAddPassword){
            DialogEnterNameSource({isOpenDialogAddPassword = false}, {key, value, des ->
                source.passwords.add(Password(key = key, value = value, description = des ))
            });
        }


        Row(modifier = Modifier.fillMaxWidth()){
            Column{
                Box(modifier = Modifier.fillMaxWidth().height(40.dp)){
                    Box(modifier = Modifier.background(Color.Transparent)
                    ){
                        BasicTextFieldBlockContainer(source)
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = Color.Transparent)
                        .clickable(onClick = { isOpenSource = !isOpenSource})
                        .padding(0.dp, 0.dp, 15.dp, 0.dp)
                        ){
                       Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){

                           Box(modifier = Modifier
                               .fillMaxHeight()
                               .padding(10.dp, 2.dp)
                               .width(30.dp)
                               .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
                               .clip(RoundedCornerShape(5.dp))
                               .clickable(onClick = { isOpenDialogAddPassword = true }),
                               contentAlignment = Alignment.Center){
                               Image(
                                   imageVector = ImageVector.vectorResource(R.drawable.plus_large),
                                   contentDescription = "+",
                                   contentScale = ContentScale.Fit,
                                   modifier = Modifier.width(20.dp),
                                   colorFilter = ColorFilter.tint(colorResource(R.color.black))
                               )
                           }

                           Image(
                               imageVector = ImageVector.vectorResource(if (isOpenSource) R.drawable.arrow_bottom else R.drawable.arrow_top),
                               contentDescription = "?",
                               contentScale = ContentScale.Fit,
                               modifier = Modifier.width(23.dp),
                               colorFilter = ColorFilter.tint(colorResource(R.color.black))
                           )


                       }
                    }
                }
                ListPasswords(source.passwords, isOpenSource)
            }
       }
    }
}

@Composable
fun DialogEnterNameSource(cancel: () -> Unit, save: (String, String, String) -> Unit){
    var key by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {cancel()},
        text = {
            Column {
                CommonBasicTextField(key, {key = it}, "Key", {})
                CommonBasicTextField(value, {value = it}, "Value", {})
                CommonBasicTextField(description, {description = it}, "Description", {})
            }
        },
        confirmButton = {
            Button({save(key, value, description); cancel();}) {
                Text("Создать", fontSize = 22.sp)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextFieldBlockContainer(source: Source_){

    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = "",
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {},
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        readOnly = true,
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = " ",
            innerTextField = innerTextField,
            enabled = false,
            singleLine = true,
            interactionSource = interactionSource,
            visualTransformation = VisualTransformation.None,
            label = { Text(
                modifier = Modifier,
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                text = source.name,
                color = Color.Black
                ) },
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







