package group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R

@Composable
fun BottomButtonsGeneratePass(generate : () -> Unit, copy: () -> Unit){
    Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.End){

        val modifier = Modifier.weight(1f)
            .height(50.dp)
            .border(width = 1.dp, color = colorResource(R.color.w_btn_border), shape = RoundedCornerShape(5.dp))
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = colorResource(R.color.w_btn_container))

        val textStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold,
            color = colorResource(R.color.w_btn_color)
        )

        Box(modifier = modifier
            .clickable { copy() }, contentAlignment = Alignment.Center){
            Text("Копировать", style = textStyle)
        }
        Spacer(Modifier.width(5.dp))
        Box(modifier = modifier
            .clickable { generate() }, contentAlignment = Alignment.Center){
            Text("Сгенерировать", style = textStyle)
        }

    }
}