package group.kalmykov.safe.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.components.homeScreen.buffer.Buffer
import group.kalmykov.safe.ui.components.homeScreen.listSources.AddSourceModal
import group.kalmykov.safe.ui.components.homeScreen.listSources.ListSources
import group.kalmykov.safe.ui.components.homeScreen.search.SearchComponent
import group.kalmykov.safe.viewModels.HomeViewModel
import kotlinx.coroutines.launch

class HomeScreen(val homeViewModel: HomeViewModel
): ViewModel(){

    private val searchComponent : SearchComponent = SearchComponent()
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
                FloatingActionButton(onClick = { isOpenDialogAddSource = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }

        ){ innerPadding ->
            if(isOpenDialogAddSource){
                AddSourceModal({ isOpenDialogAddSource = false }, { source -> homeViewModel.sourceRepository.add(source)})
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