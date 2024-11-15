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
import group.kalmykov.safe.db.AppDd
import group.kalmykov.safe.models.Routes

class MainViewModel(private var navController: NavHostController, context: Context) : ViewModel(){
  //  val dbHelper: DbHelper = DbHelper(context)

    val db = AppDd.getInstance(context.applicationContext)

    private var loginViewModel = LoginViewModel(navController)
    private var homeViewModel = HomeViewModel(this)
    private var welcomeViewModel = WelcomeViewModel(navController)
    private var introductionViewModel = IntroductionViewModel()
    private var createPinCodeViewModel = CreatePinCodeViewModel()

    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

   // val dbContext: DbContext = DbContext(context)

    @Composable
    fun NavHostContainer(){

        NavHost(
            navController = this.navController,
            startDestination = if(isFirstLaunch()) Routes.Welcome.route else  Routes.Welcome.route,
            modifier = Modifier,

            builder = {
                composable(Routes.Home.route) {homeViewModel.ShowScreen()}
                composable(Routes.Login.route) {loginViewModel.ShowScreen()}
                composable(Routes.Introduction.route) {introductionViewModel.ShowScreen()}
                composable(Routes.CreatePinCode.route) {createPinCodeViewModel.ShowScreen()}
                composable(Routes.Welcome.route) {welcomeViewModel.ShowScreen()}
            })
    }

    private val isFirstLaunchFlagKey = "isFirstLaunch"

    private fun isFirstLaunch(): Boolean {
        // Проверяем значение флага "isFirstLaunch"
        val isFirstLaunch = sharedPreferences.getBoolean(isFirstLaunchFlagKey, true)

        if (isFirstLaunch) {
            // Если это первый запуск, устанавливаем флаг в false
            sharedPreferences.edit().putBoolean(isFirstLaunchFlagKey, false).apply()
        }

        return isFirstLaunch
    }


}