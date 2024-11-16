package group.kalmykov.safe.viewModels

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.ui.screens.HomeScreen


class HomeViewModel(mainViewModel : MainViewModel) : ViewModel() {

    private val sourceDao = mainViewModel.db.sourceDao()

    private val homeScreen : HomeScreen = HomeScreen(sourceDao)

    @SuppressLint("Recycle")
    @Composable
    fun ShowScreen(){
        homeScreen.Show()
    }



}

