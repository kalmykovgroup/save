package group.kalmykov.safe.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.screens.homeScreen.components.BottomBarContainer
import group.kalmykov.safe.ui.screens.homeScreen.components.FloatingActionButtonContainer
import group.kalmykov.safe.ui.screens.homeScreen.components.Search
import group.kalmykov.safe.ui.screens.homeScreen.components.TopAppBarContainer
import group.kalmykov.safe.ui.screens.homeScreen.components.buffer.Buffer
import group.kalmykov.safe.ui.screens.homeScreen.components.list.ListSources
import group.kalmykov.safe.viewModels.HomeViewModel


@Composable
fun HomeScreen(homeViewModel: HomeViewModel){

   Scaffold(
        topBar = { TopAppBarContainer() } ,
        floatingActionButton = { FloatingActionButtonContainer(homeViewModel) },
        bottomBar = { BottomBarContainer(homeViewModel)}

    ){ innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .background(color = colorResource(R.color.background_home_screen)), contentAlignment = Alignment.TopStart
        ) {
            Column {
                Search(homeViewModel)

                Buffer()
                Box(modifier = Modifier.fillMaxWidth().height(20.dp), contentAlignment = Alignment.CenterStart){
                    Text(text = "@Composable", color = colorResource(R.color.composable), style = TextStyle(fontSize = 10.sp))
                }
                ListSources(homeViewModel)
            }
        }
    }
}

/*class HomeScreen(homeViewModel: HomeViewModel): ViewModel(){

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
  if(listSources.isSelectedSources) {
                    FloatingActionButton(onClick = { isOpenDialogAddSource = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }

            },
            bottomBar = {
if(listSources.isSelectedSources){

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
                                            sourceRepository.deleteAll(listSources.listIdSelectedSources)
                                            listSources.listIdSelectedSources.clear()
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
                                   listSources.listIdSelectedSources.clear()
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



}*/
