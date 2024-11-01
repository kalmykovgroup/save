package group.kalmykov.safe.ui.screens

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.components.homeScreen.bottomBar.BottomBarComponent
import group.kalmykov.safe.ui.components.homeScreen.buffer.Buffer
import group.kalmykov.safe.ui.components.homeScreen.listSources.ListSources
import group.kalmykov.safe.ui.components.homeScreen.search.SearchComponent
import group.kalmykov.safe.ui.components.homeScreen.sourceList.ListSourcesComponent
import group.kalmykov.safe.viewModels.HomeViewModel
import kotlinx.coroutines.launch

class HomeScreen(homeViewModel: HomeViewModel
): ViewModel(){

    private val searchComponent : SearchComponent = SearchComponent()
    private val listSources : ListSources = ListSources(this)
    val listSourcesComponent : ListSourcesComponent = ListSourcesComponent()
    private val bottomBarComponent : BottomBarComponent = BottomBarComponent(homeViewModel)



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun Show(){


        Scaffold(content = {


           Box(
                modifier = Modifier
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
                   // sourceListComponent.Show()
                }
            }
        },
            bottomBar = {
              //  bottomBarComponent.Show()
            })
    }




}