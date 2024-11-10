package group.kalmykov.safe.viewModels

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.room.Room
import group.kalmykov.safe.db.AppDd
import group.kalmykov.safe.entity.Source
import group.kalmykov.safe.repository.SourceRepository
import group.kalmykov.safe.ui.screens.HomeScreen
import kotlinx.coroutines.launch


class HomeViewModel(mainViewModel : MainViewModel) : ViewModel() {

    private val sourceDao = mainViewModel.db.sourceDao()
    val sourceRepository: SourceRepository = SourceRepository(sourceDao)

    private val homeScreen : HomeScreen = HomeScreen(this)


    @SuppressLint("Recycle")
    @Composable
    fun ShowScreen(){

        homeScreen.Show()
    }



}

