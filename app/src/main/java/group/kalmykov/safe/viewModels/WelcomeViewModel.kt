package group.kalmykov.safe.viewModels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import group.kalmykov.safe.models.Routes
import group.kalmykov.safe.ui.screens.WelcomeScreen

class WelcomeViewModel(private var navController: NavHostController): ViewModel() {

    private var isLoadScreen by mutableStateOf(false)

    @Composable
    fun ShowScreen(){
        WelcomeScreen({ navController.navigate(Routes.CreatePinCode.route); isLoadScreen = true}, { navController.navigate(Routes.Introduction.route); isLoadScreen = true }, isLoadScreen)
    }
}