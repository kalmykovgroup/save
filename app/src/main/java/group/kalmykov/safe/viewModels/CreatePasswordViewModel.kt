package group.kalmykov.safe.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.navigation.Destinations
import group.kalmykov.safe.navigation.NavGraphViewModel

class CreatePasswordViewModel(context: Context, private val navGraphViewModel: NavGraphViewModel): ViewModel() {



    fun savePassword(password: String) {

        // Сохранение пароля
        navGraphViewModel.loginViewModel.saveEncryptedPassword(password)

        //Сохранили информацию о том что мы уже заходили и создали пароль для входа
        navGraphViewModel.loginViewModel.successfulFirstLogin()


        navGraphViewModel.navController.navigate(Destinations.Home.route){
            popUpTo(Destinations.About.route) { inclusive = true }
            popUpTo(Destinations.CreatePassword.route) { inclusive = true }
            popUpTo(Destinations.FirstLogin.route) { inclusive = true }
        }

    }


}