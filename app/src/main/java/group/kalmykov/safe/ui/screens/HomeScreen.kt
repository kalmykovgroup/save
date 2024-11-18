package group.kalmykov.safe.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.dao.SourceDao
import group.kalmykov.safe.repository.SourceRepository
import group.kalmykov.safe.ui.components.homeScreen.buffer.Buffer
import group.kalmykov.safe.ui.components.homeScreen.listSources.AddSourceDialog
import group.kalmykov.safe.ui.components.homeScreen.listSources.ListSources
import group.kalmykov.safe.ui.components.homeScreen.search.SearchComponent

class HomeScreen(sourceDao: SourceDao): ViewModel(){

    val searchComponent : SearchComponent = SearchComponent()

    val sourceRepository: SourceRepository = SourceRepository(sourceDao)

    private val listSources : ListSources = ListSources(this)



    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun Show(){

        var isOpenDialogAddSource by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth().height(30.dp),
                    colors = topAppBarColors(
                        containerColor = colorResource(R.color.background_home_screen),
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {

                    }
                )
            } ,
            floatingActionButton = {
                if(listSources.selectedList.size == 0) {
                    FloatingActionButton(onClick = { isOpenDialogAddSource = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            },
            bottomBar = {
                if(listSources.selectedList.size > 0){

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
                                        onClick = { deleteSources = false}
                                    ) {
                                        Text("Отменить", fontSize = 17.sp)
                                    }

                                    Button(
                                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                                        onClick = {
                                            sourceRepository.deleteAll(listSources.selectedList.map { item -> item.id })
                                            listSources.selectedList.clear()
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
                                   listSources.selectedList.clear()
                               }){
                               Text(text = "Отменить")
                           }
                       }
                    }
                }
            }

        ){ innerPadding ->
            if(isOpenDialogAddSource){
                AddSourceDialog({ isOpenDialogAddSource = false }, { source -> sourceRepository.add(source)})
            }

           Box(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
                    .background(color = colorResource(R.color.background_home_screen)), contentAlignment = Alignment.TopStart
            ) {

                Column {
                    searchComponent.Show()

                    Buffer()
                    Box(modifier = Modifier.fillMaxWidth().height(20.dp), contentAlignment = Alignment.CenterStart){
                        Text(text = "@Composable", color = colorResource(R.color.composable), style = TextStyle(fontSize = 10.sp))
                    }
                    listSources.Show()
                }
            }
        }
    }



}