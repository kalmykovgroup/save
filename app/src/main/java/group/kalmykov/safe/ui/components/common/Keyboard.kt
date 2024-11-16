package group.kalmykov.safe.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R


@Composable
fun Keyboard(
    leftBox: (@Composable () -> Unit)? = null,
    rightBox: (@Composable () -> Unit)? = null,
    isRightBoxActive: Boolean = false,
    keyDelClick: () -> Unit,
    keyClick: (Int) -> Unit,
    keyDelColorFilter: ColorFilter? = null) {
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
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(12) {
            index ->
            if(index == 9){
                if(leftBox != null){
                    Box(modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        , contentAlignment = Alignment.Center){
                        leftBox()
                    }
                }

                return@items
            }

            if(index == 11){
                Box(modifier = Modifier.height(80.dp).fillMaxWidth(), contentAlignment = Alignment.Center){

                    if(isRightBoxActive && rightBox != null){
                            rightBox()
                    }
                    else {
                        //Кнопка удалить
                        Button(
                            onClick = { keyDelClick() },
                            modifier = Modifier
                                .height(80.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent),
                            contentPadding = PaddingValues()

                        ){
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.delete),
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(50.dp, 50.dp),
                                    colorFilter = keyDelColorFilter
                                )
                            }
                        }
                    }
                }
                return@items
            }

            Button(
                onClick = { keyClick(index + 1) },
                modifier = Modifier.height(80.dp),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(2.dp),
                colors = ButtonDefaults.buttonColors( containerColor = colorResource(R.color.passButton))
            ){
                Column {
                    Box(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(2f).fillMaxWidth(), contentAlignment = Alignment.Center){
                        Text(
                            text = "${(index + 1) % 11}",
                            fontSize = 25.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
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