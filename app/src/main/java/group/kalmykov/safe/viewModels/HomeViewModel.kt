package group.kalmykov.safe.viewModels

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import group.kalmykov.safe.models.Password
import group.kalmykov.safe.models.Source
import group.kalmykov.safe.ui.screens.HomeScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class HomeViewModel() : ViewModel() {

    val homeScreen : HomeScreen = HomeScreen(this)

    @Composable
    fun ShowScreen(){
        homeScreen.Show()
    }

}