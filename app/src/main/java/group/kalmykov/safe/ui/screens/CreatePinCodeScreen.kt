package group.kalmykov.safe.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.ui.components.common.Keyboard

@Composable
fun CreatePinCodeScreen() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column {
            Keyboard({LeftBox()}, {RightBox()}, {})
        }
    }
}

@Composable
fun LeftBox(){
        Text(text = "Не могу войти")

}

@Composable
fun RightBox(){

}