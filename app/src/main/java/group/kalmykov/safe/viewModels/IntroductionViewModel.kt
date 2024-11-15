package group.kalmykov.safe.viewModels

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.ui.screens.IntroductionScreen

class IntroductionViewModel: ViewModel() {

    @Composable
    fun ShowScreen(){
        IntroductionScreen()
    }
}