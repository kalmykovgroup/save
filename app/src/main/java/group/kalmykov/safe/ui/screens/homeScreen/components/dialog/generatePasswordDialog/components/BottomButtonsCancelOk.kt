package group.kalmykov.safe.ui.screens.homeScreen.components.dialog.generatePasswordDialog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomButtonsCancelOk(cancel: () -> Unit, ok: () -> Unit){
    Row (modifier = Modifier.fillMaxWidth().padding(top = 40.dp),  horizontalArrangement = Arrangement.End){


        Button(
            modifier = Modifier.padding(5.dp, 0.dp).fillMaxWidth().weight(1f), shape = RoundedCornerShape(5.dp),
            onClick = {cancel()}
        ) {
            Text("Отменить", fontSize = 17.sp)
        }

        Button(
            modifier = Modifier.padding(5.dp, 0.dp).fillMaxWidth().weight(1f), shape = RoundedCornerShape(5.dp),
            onClick = { ok(); }
        ) {
            Text("Ок", fontSize = 17.sp)
        }
    }
}