package group.kalmykov.safe.ui.components.homeScreen.sourceList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.models.Source


class SourceListComponent(): ViewModel(){

    var sources: MutableList<Source> = mutableStateListOf(
        Source("Source_1"),
        Source("Source_2")
    )


    @Composable
    fun Show(){
        LazyColumn {
            items(sources){ source ->
                SourceRow(source)
            }
        }
    }

    @Composable
    fun SourceRow(source: Source){
        Row(modifier = Modifier.fillMaxWidth().height(40.dp)){
            Box{
                Box{
                    BasicTextFieldBlock(source)
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(5.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color = Color.Transparent),
                    contentAlignment = Alignment.CenterEnd){
                     Row(horizontalArrangement = Arrangement.End){
                         PasswordList(source.passwords)
                     }
                }
            }
       }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextFieldBlock(source: Source){

    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = " ",
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Color.White),
        onValueChange = {   },
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
            label = { Text(softWrap = false,
                overflow = TextOverflow.Ellipsis,
                text = source.name, color = Color.Black) },
            container = {
                OutlinedTextFieldDefaults.ContainerBox(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.border_source_item_focused),
                        unfocusedBorderColor = colorResource(R.color.border_source_item_unfocused),

                        ),
                    shape = RoundedCornerShape(6.dp),
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp,
                )
            }
        )
    }
}







