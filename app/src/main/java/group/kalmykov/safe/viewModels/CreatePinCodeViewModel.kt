package group.kalmykov.safe.viewModels

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import group.kalmykov.safe.models.Routes
import group.kalmykov.safe.ui.screens.CreatePinCodeScreen

class CreatePinCodeViewModel(private val callBack : (List<Int>) -> Unit): ViewModel() {


    @Composable
    fun ShowScreen(){
        CreatePinCodeScreen(savePassword = {pinKod -> callBack(pinKod);} )
    }

}