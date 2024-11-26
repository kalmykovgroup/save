package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R

@Composable
fun InputContainer(label: String, attributes : @Composable () -> Unit, text: String, visualText: Boolean = true){


    Row(modifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .background(color = colorResource(R.color.background_field_resource))
        .height(60.dp)
        .padding(start = 10.dp, top = 3.dp, bottom = 3.dp),
        verticalAlignment = Alignment.Bottom
    ){
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()){

            Text(text = label ,
                style = TextStyle( fontSize = 12.sp, color = colorResource(R.color.color_label_field_resource)),
                modifier = Modifier
                    .align(AbsoluteAlignment.TopLeft)
                    .offset(y = (2).dp)
            )

            Box(modifier = Modifier
                .fillMaxWidth().fillMaxHeight().padding(top = 10.dp), contentAlignment = Alignment.CenterStart){

                val scrollState = rememberScrollState()

                Text(
                    text = if(visualText) text else "*".repeat(text.length),
                    color = colorResource(R.color.color_value_field_resource),
                    style = TextStyle(fontSize = 16.sp, fontWeight = if(visualText) FontWeight.Normal else FontWeight.Bold),
                    modifier = Modifier
                        .horizontalScroll(scrollState),
                )

            }
        }
        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically){
            attributes()
        }

    }
}