package group.kalmykov.safe.ui.components.common

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R


@Composable
fun Keyboard(leftBox: @Composable () -> Unit, rightBox: @Composable () -> Unit, keyClick: (Int) -> Unit) {
    val buttons: Array<String> = arrayOf(
        "а б в г",
        "д е ж з",
        "и й к л",
        "м н о п",
        "р с т у",
        "ф х ц ч",
        "ш щ ъ ы",
        "ь э ю я",
    )

    LazyVerticalGrid(
        modifier = Modifier
            .padding(10.dp, 50.dp, 10.dp, 2.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(12) {
            index ->

            if(index == 9){
                Box(modifier = Modifier.height(80.dp).fillMaxWidth(), contentAlignment = Alignment.Center){
                    leftBox()
                }
                return@items
            }

            if(index == 11){
                Box(modifier = Modifier.height(80.dp).fillMaxWidth(), contentAlignment = Alignment.Center){
                    rightBox()
                }
                return@items
            }

            Button(
                onClick = { keyClick(index + 1) },
                modifier = Modifier.height(80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors( containerColor = colorResource(R.color.passButton))
            ){
                Column {
                    Box(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center){
                        Text(
                            text = "${(index + 1) % 11}",
                            fontSize = 25.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Box(modifier = Modifier.height(20.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if(index + 1 > 0 && index + 1 <= buttons.size) buttons[index] else "",
                            fontSize = 10.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

    }
}