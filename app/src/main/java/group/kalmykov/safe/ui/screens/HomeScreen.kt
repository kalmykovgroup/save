package group.kalmykov.safe.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.ui.components.homeScreen.bottomBar.BottomBarComponent
import group.kalmykov.safe.ui.components.homeScreen.search.SearchComponent
import group.kalmykov.safe.ui.components.homeScreen.sourceList.ListSourcesComponent
import group.kalmykov.safe.viewModels.HomeViewModel

class HomeScreen(private val homeViewModel: HomeViewModel): ViewModel(){

    private val searchComponent : SearchComponent = SearchComponent()
    val sourceListComponent : ListSourcesComponent = ListSourcesComponent()
    private val bottomBarComponent : BottomBarComponent = BottomBarComponent(homeViewModel)


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun Show(){
        Scaffold(content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White), contentAlignment = Alignment.TopStart
            ) {

                Column {
                    searchComponent.Show()
                    sourceListComponent.Show()
                }
            }
        },
            bottomBar = {
                bottomBarComponent.Show()
            })
    }

}