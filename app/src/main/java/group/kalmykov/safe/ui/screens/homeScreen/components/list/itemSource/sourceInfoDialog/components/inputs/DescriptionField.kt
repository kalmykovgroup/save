package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.BtnCopy
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.InputContainer


@Composable
fun DescriptionField(source: Source){

    Row(modifier = Modifier
        .padding(0.dp, 5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .background(color = colorResource(R.color.background_field_resource))
        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
        ,verticalAlignment = Alignment.Top
    ){
        Box(modifier = Modifier
            .weight(1f)
            .padding(start = 10.dp, top = 3.dp, bottom = 3.dp)){

            Text(text = "Description" ,
                style = TextStyle( fontSize = 12.sp, color = colorResource(R.color.color_label_field_resource)),
                modifier = Modifier
                    .align(AbsoluteAlignment.TopLeft)
                    .offset(y = (2).dp)
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp).padding(top = 15.dp)){

                Text(
                    text = source.description?: "",
                    color = colorResource(R.color.color_value_field_resource),
                    fontSize = 16.sp,
                )

            }
        }
        BtnCopy(value = source.description)
    }
}