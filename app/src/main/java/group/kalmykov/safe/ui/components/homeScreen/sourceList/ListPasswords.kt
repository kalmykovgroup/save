package group.kalmykov.safe.ui.components.homeScreen.sourceList
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.models.Password


@Composable
fun DialogDeletePassword(cancel: () -> Unit, delete: () -> Unit){
    AlertDialog(
        onDismissRequest = {cancel()},
        title = { Text(text = "Подтверждение действия") },
        text = { Text("Вы действительно хотите удалить выбранный элемент?") },
        confirmButton = {
            Button({delete(); cancel();}) {
                Text("OK", fontSize = 22.sp)
            }
        },
        dismissButton = {
            Button(cancel) {
                Text("Cancel", fontSize = 22.sp)
            }
        }
    )
}

@Composable
fun ListPasswords(passwords: MutableList<Password>, isOpenSource: Boolean){
    var openDialogDelete by remember { mutableStateOf(false) }

    var passwordDelete : Password? by remember { mutableStateOf(null) }

    if (openDialogDelete) {
        DialogDeletePassword({openDialogDelete = false}, {
            passwords.remove(passwordDelete)
        })
    }

    AnimatedVisibility(
        visible = isOpenSource,
        enter = expandVertically(expandFrom = Alignment.CenterVertically),
        exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
      ) {
        Column(modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 30.dp)
        ) {

            passwords.forEach { password ->
                PasswordItemUI(password) { passwordDelete = it; openDialogDelete = true }
            };
        }
    }



}


