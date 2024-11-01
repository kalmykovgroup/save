package group.kalmykov.safe.ui.components.loginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.viewModels.LoginViewModel

@Composable
fun InputButtons(loginViewModel : LoginViewModel) {
    val buttons: Array<Pair<Int, String>> = arrayOf(
        Pair(1, ""),
        Pair(2, "а б в г"),
        Pair(3, "д е ж з"),
        Pair(4, "и й к л"),
        Pair(5, "м н о п"),
        Pair(6, "р с т у"),
        Pair(7, "ф х ц ч"),
        Pair(8, "ш щ ъ ы"),
        Pair(9, "ь э ю я"),
    )
    val context = LocalContext.current

    LazyVerticalGrid(
        modifier = Modifier
            .padding(10.dp, 50.dp, 10.dp, 2.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(buttons) {item ->

            Button(
                onClick = { loginViewModel.Enter(item.first, context) },
                modifier = Modifier.height(80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors( containerColor = colorResource(R.color.passButton))
            ){
                Column{
                    Text(
                        text = "${item.first}",
                        fontSize = 25.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        text = item.second,
                        fontSize = 10.sp,
                        color = Color.Black
                    )
                }
            }
        }


    }
}