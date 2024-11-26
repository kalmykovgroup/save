package group.kalmykov.safe.ui.screens.homeScreen.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeleteItemDialog(visual: Boolean,  setVisual: (Boolean) -> Unit, successClick: () -> Unit,) {
    if(visual){
        AlertDialog(
            onDismissRequest = {setVisual(false)},
            title = { Text(text = "Подтверждение действия") },
            text = { Text("Вы действительно хотите удалить выбранный элемент?") },
            confirmButton = {
                Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.End){
                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick =  {setVisual(false)}
                    ) {
                        Text("Отменить", fontSize = 17.sp)
                    }

                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick = { setVisual(false); successClick() }
                    ) {
                        Text("Ok", fontSize = 17.sp)
                    }
                }
            }
        )
    }
}