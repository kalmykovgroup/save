package group.kalmykov.safe.viewModels

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import group.kalmykov.safe.functions.vibrator
import group.kalmykov.safe.models.Routes
import group.kalmykov.safe.ui.screens.LoginScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private var navController: NavHostController) : ViewModel() {

    var passBoxArray = mutableStateListOf<Int?>(null, null, null, null, null)
    var currentIndex by mutableStateOf(0)
    var isSuccess by mutableStateOf<Boolean?>(null)

    @Composable
    fun ShowScreen(){
        LoginScreen(this)
    }

    val pin  = "11111";

   private fun SingIn(pin : String): Boolean {

       return this.pin == pin;

    }

    fun Delete(){

        if(currentIndex > 0) passBoxArray[--currentIndex] = null
    }

    fun Enter(digit: Int, context : Context){
            if(currentIndex < 0 || currentIndex >= passBoxArray.size) return

            passBoxArray[currentIndex++] = digit

            if(currentIndex == passBoxArray.size){

                val numberStr = passBoxArray.joinToString("")
                if(SingIn(numberStr)){

                    isSuccess = true

                    viewModelScope.launch(Dispatchers.Main) {
                        delay(300)
                        navController.navigate(Routes.Home.route);
                    }


                }else{
                    vibrator(context)

                    isSuccess = false

                    viewModelScope.launch(Dispatchers.Main) {
                        delay(300)
                        isSuccess = null
                        currentIndex = 0
                        passBoxArray.replaceAll{null}
                    }


                }
            }

    }

}