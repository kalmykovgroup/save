package group.kalmykov.safe.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import group.kalmykov.safe.data.db.AppDatabase
import group.kalmykov.safe.ui.screens.AboutScreen
import group.kalmykov.safe.ui.screens.CreatePasswordScreen
import group.kalmykov.safe.ui.screens.HomeScreen
import group.kalmykov.safe.ui.screens.firstLoginScreen.FirstLoginScreen
import group.kalmykov.safe.ui.screens.loginScreen.LoginScreen
import group.kalmykov.safe.viewModels.AboutViewModel
import group.kalmykov.safe.viewModels.CreatePasswordViewModel
import group.kalmykov.safe.viewModels.FirstLoginViewModel
import group.kalmykov.safe.viewModels.HomeViewModel
import group.kalmykov.safe.viewModels.LoginViewModel

class NavGraphViewModel(context: Context, val navController: NavController): ViewModel(){

    val db = AppDatabase.getInstance(context.applicationContext)

    val firstLoginViewModel: FirstLoginViewModel = FirstLoginViewModel(context, this)
    val createPasswordViewModel: CreatePasswordViewModel = CreatePasswordViewModel(context, this)
    val aboutViewModel: AboutViewModel = AboutViewModel(context, this)
    val loginViewModel: LoginViewModel = LoginViewModel(context, this)
    val homeViewModel: HomeViewModel = HomeViewModel(context, this)

}

@Composable
fun NavGraph(context: Context) {

    val navController = rememberNavController()

    val navGraphViewModel = NavGraphViewModel(context, navController)

    NavHost(
        navController = navController,
        startDestination = if(navGraphViewModel.loginViewModel.isFirstLogin()) Destinations.FirstLogin.route else  Destinations.Home.route,
        modifier = Modifier,

        builder = {
            composable(Destinations.Home.route) { HomeScreen(navGraphViewModel.homeViewModel) }
            composable(Destinations.Login.route) { LoginScreen(navGraphViewModel.loginViewModel) }
            composable(Destinations.About.route) { AboutScreen(navGraphViewModel.aboutViewModel) }
            composable(Destinations.CreatePassword.route) { CreatePasswordScreen(navGraphViewModel.createPasswordViewModel) }
            composable(Destinations.FirstLogin.route) { FirstLoginScreen(navGraphViewModel.firstLoginViewModel) }
        }
    )
}