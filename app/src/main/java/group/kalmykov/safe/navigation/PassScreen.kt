package group.kalmykov.safe.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R


@Composable
fun PassScreen() {

    val pass = remember { mutableStateListOf(-1, -1, -1, -1, -1) }
    var currentIndex = remember {0}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(R.drawable.pass_background), contentScale = ContentScale.Crop),
        contentAlignment = Alignment.Center

    ) {

        Column {

            LazyRow(Modifier, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                 items(pass) { item ->

                     Box(modifier = Modifier
                         .width(50.dp)
                         .height(50.dp)
                         .clip(RoundedCornerShape(10.dp))
                         .background(Color.LightGray),
                         contentAlignment = Alignment.Center
                     ) {

                         if(item >= 0){
                             Image(
                                 imageVector = ImageVector.vectorResource(R.drawable.pass_icon),
                                 contentDescription = "*",
                                 modifier = Modifier.size(20.dp, 20.dp)
                             )
                         }

                     }

                 }
             }

            val buttons: Array<Pair<Int, String>> = arrayOf(
                Pair(1, ""),
                Pair(2, "а б в г"),
                Pair(3, "д е ж з"),
                Pair(4, "и й к л"),
                Pair(5, "м н о п"),
                Pair(6, "р с т у"),
                Pair(7, "ф х ц ч"),
                Pair(8, "ш щ ъ ы"),
                Pair(9, "ь э ю я"),
            )

            LazyVerticalGrid(
                modifier = Modifier.padding(10.dp, 50.dp, 10.dp, 2.dp)
                    .background(color = Color.Transparent),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                columns = GridCells.Fixed(3)
            ) {
                itemsIndexed(buttons) { index, item ->

                        Button(
                            onClick = { if(currentIndex < pass.size) pass[currentIndex++] = item.first},
                            modifier = Modifier
                                .height(80.dp),
                            shape = RoundedCornerShape(20.dp),

                            colors = ButtonDefaults.buttonColors( containerColor = colorResource(R.color.passButton))
                        ){
                            Column(){
                                Text(
                                    text = "${item.first}",
                                    fontSize = 25.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = item.second,
                                    fontSize = 10.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }


            }

            Row(modifier = Modifier.padding(10.dp, 0.dp)
                .background(color = Color.Transparent),
                horizontalArrangement = Arrangement.spacedBy(2.dp) ){

                //Кнопка Не могу войти
                Box(modifier = Modifier.height(80.dp).weight(1f), contentAlignment = Alignment.Center){
                    Button(
                        onClick = { if(currentIndex > 0) pass[--currentIndex] = -1 },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                        .height(80.dp).fillMaxWidth() ,
                      colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ){
                        Text(
                            modifier = Modifier.width(70.dp).fillMaxWidth(),
                            text = "Не могу войти",
                            fontSize = 17.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }

                }
                //Кнопка ноль
                Box(modifier = Modifier.height(80.dp).weight(1f), contentAlignment = Alignment.Center){
                    Button(
                        onClick = { if(currentIndex < pass.size) pass[currentIndex++] = 0},
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors( containerColor = colorResource(R.color.passButton))
                     ){
                        Text(
                            text = "0",
                            fontSize = 22.sp,
                            color = Color.Black
                        )
                    }
                }
                //Кнопка удалить
                Box(modifier = Modifier
                    .height(80.dp)
                    .weight(1f), contentAlignment = Alignment.Center){
                    Button(
                        onClick = { if(currentIndex > 0) pass[--currentIndex] = -1 },
                        modifier = Modifier
                        .height(80.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent)

                    ){
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.delete),
                            contentDescription = "Отпечаток",
                            modifier = Modifier.size(50.dp, 50.dp)

                        )
                    }
                }
            }




        }

    }
}


