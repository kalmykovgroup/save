package group.kalmykov.safe.viewModels

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.ui.screens.HomeScreen


class HomeViewModel : ViewModel() {

    val homeScreen : HomeScreen = HomeScreen(this)

    @Composable
    fun ShowScreen(){
        homeScreen.Show()
    }

}