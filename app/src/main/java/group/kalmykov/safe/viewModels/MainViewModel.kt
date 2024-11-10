package group.kalmykov.safe.viewModels

import android.content.Context
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
import group.kalmykov.safe.repository.SourceRepository

class MainViewModel(private var navController: NavHostController, context: Context) : ViewModel(){
  //  val dbHelper: DbHelper = DbHelper(context)

    val db = AppDd.getInstance(context.applicationContext)

    private var LoginViewModel = LoginViewModel(navController);
    private var homeViewModel = HomeViewModel(this);



   // val dbContext: DbContext = DbContext(context)

    @Composable
    fun NavHostContainer(){
        NavHost(
            navController = this.navController,
            startDestination = Routes.Home.route,
            modifier = Modifier,

            builder = {
                composable(Routes.Home.route) {homeViewModel.ShowScreen()}
                composable(Routes.Login.route) {LoginViewModel.ShowScreen()}
            })
    }

    override fun onCleared() {

       // dbHelper.close()

        super.onCleared()
    }




}