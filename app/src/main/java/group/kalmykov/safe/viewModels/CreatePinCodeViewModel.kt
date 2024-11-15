package group.kalmykov.safe.viewModels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.ui.screens.CreatePinCodeScreen

class CreatePinCodeViewModel: ViewModel() {

    @Composable
    fun ShowScreen(){
        CreatePinCodeScreen()
    }
}