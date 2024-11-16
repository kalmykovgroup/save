package group.kalmykov.safe.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import group.kalmykov.safe.db.AppDd
import group.kalmykov.safe.models.Routes

class MainViewModel(private var navController: NavHostController, context: Context) : ViewModel(){
  //  val dbHelper: DbHelper = DbHelper(context)

    val db = AppDd.getInstance(context.applicationContext)

    private val isFirstLaunchFlagKey = "isFirstLaunch"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    private var loginViewModel = LoginViewModel(callbackOnSuccessfulLogin = { callbackOnSuccessfulLogin() }, this, context)
    private var homeViewModel = HomeViewModel(mainViewModel = this)
    private var welcomeViewModel = WelcomeViewModel(navController = navController)
    private var introductionViewModel = IntroductionViewModel()
    private var createPinCodeViewModel = CreatePinCodeViewModel(callBack = {createPinCodeCallBackSuccess(it)})



    @Composable
    fun NavHostContainer(){

        NavHost(
            navController = this.navController,
            startDestination = if(isFirstLaunch()) Routes.Welcome.route else  Routes.Home.route,
            modifier = Modifier,

            builder = {
                composable(Routes.Home.route) {homeViewModel.ShowScreen()}
                composable(Routes.Login.route) {loginViewModel.ShowScreen()}
                composable(Routes.Introduction.route) {introductionViewModel.ShowScreen()}
                composable(Routes.CreatePinCode.route) {createPinCodeViewModel.ShowScreen()}
                composable(Routes.Welcome.route) {welcomeViewModel.ShowScreen()}
            })
    }

    private fun callbackOnSuccessfulLogin(){
        navController.navigate(Routes.Home.route){
            popUpTo(Routes.Login.route) { inclusive = true }
        }
    }

    private fun createPinCodeCallBackSuccess(pinCode: List<Int>) {

        // Сохранение пароля
        loginViewModel.saveEncryptedPassword(pinCode.joinToString(""))

        //Сохранили информацию о том что мы уже заходили и создали пароль для входа
        sharedPreferences.edit().putBoolean(isFirstLaunchFlagKey, false).apply()


        navController.navigate(Routes.Home.route){
            popUpTo(Routes.Introduction.route) { inclusive = true }
            popUpTo(Routes.CreatePinCode.route) { inclusive = true }
            popUpTo(Routes.Welcome.route) { inclusive = true }
        }

    }
    private fun isFirstLaunch(): Boolean {//Проверяем что это не первый вход
        return sharedPreferences.getBoolean(isFirstLaunchFlagKey, true)
    }

    fun restartApp(){

    }


}