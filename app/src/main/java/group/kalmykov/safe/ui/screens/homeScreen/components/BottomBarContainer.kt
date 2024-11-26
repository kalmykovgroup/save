package group.kalmykov.safe.ui.screens.homeScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun BottomBarContainer(homeViewModel: HomeViewModel){
    if(homeViewModel.isSelectedMode){

        var deleteSources: Boolean by  remember { mutableStateOf(false) }

        if (deleteSources) {
            AlertDialog(
                onDismissRequest = { deleteSources = false},
                title = { Text(text = "Подтверждение действия") },
                text = { Text("Вы действительно хотите удалить выбранные элементы?") },
                confirmButton = {
                    Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.End){
                        Button(
                            modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                            onClick = {
                                homeViewModel.listSelectedSources.clear()
                                deleteSources = false
                            }
                        ) {
                            Text("Отменить", fontSize = 17.sp)
                        }

                        Button(
                            modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                            onClick = {
                                homeViewModel.deleteSelected()
                            }
                        ) {
                            Text("Ok", fontSize = 17.sp)
                        }
                    }
                }
            )
        }

        BottomAppBar(
            containerColor = Color.DarkGray,
            contentColor = Color.LightGray
        ){
            Row(modifier = Modifier.fillMaxSize()){
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f).fillMaxHeight().clickable {
                        deleteSources = true
                    }){
                    Text(text = "Удалить")
                }
                VerticalDivider(thickness = 0.5.dp, color = Color.Gray)
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f).fillMaxHeight().clickable {
                        homeViewModel.isSelectedMode
                        homeViewModel.listSelectedSources.clear()
                    }){
                    Text(text = "Отменить")
                }
            }
        }
    }
}