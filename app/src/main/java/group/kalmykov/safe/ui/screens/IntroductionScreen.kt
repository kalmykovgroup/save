package group.kalmykov.safe.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun IntroductionScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = "IntroductionScreen", color = Color.Black)
    }
}