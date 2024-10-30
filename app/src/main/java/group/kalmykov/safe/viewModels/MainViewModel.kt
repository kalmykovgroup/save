package group.kalmykov.safe.viewModels

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import group.kalmykov.safe.models.Routes

class MainViewModel(private var navController: NavHostController, private var padding: PaddingValues, context: Context) : ViewModel(){

    private var LoginViewModel = LoginViewModel(navController, context);
    private var homeViewModel = HomeViewModel();

    @Composable
    fun NavHostContainer(){
        NavHost(
            navController = this.navController,
            startDestination = Routes.Login.route,
            modifier = Modifier.padding(paddingValues = padding),

            builder = {
                composable(Routes.Home.route) {homeViewModel.ShowScreen()}
                composable(Routes.Login.route) {LoginViewModel.ShowScreen()}
            })
    }



}