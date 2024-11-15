package group.kalmykov.safe.viewModels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import group.kalmykov.safe.models.Routes
import group.kalmykov.safe.ui.screens.WelcomeScreen

class WelcomeViewModel(private var navController: NavHostController): ViewModel() {

    @Composable
    fun ShowScreen(){
        WelcomeScreen({ navController.navigate(Routes.CreatePinCode.route)}, { navController.navigate(Routes.Introduction.route) })
    }
}