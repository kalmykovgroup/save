package group.kalmykov.safe.viewModels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.navigation.Destinations
import group.kalmykov.safe.navigation.NavGraphViewModel

class FirstLoginViewModel(context: Context, private val navGraphViewModel: NavGraphViewModel): ViewModel() {

    val text: String = "Hello world!"

    var isLoadScreen by mutableStateOf(false)

    fun navSkip(){
        navGraphViewModel.navController.navigate(Destinations.CreatePassword.route);
    }

    fun navAbout(){
        navGraphViewModel.navController.navigate(Destinations.About.route);
    }


}