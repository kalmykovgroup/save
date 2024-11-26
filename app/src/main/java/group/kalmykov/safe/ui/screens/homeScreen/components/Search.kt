package group.kalmykov.safe.ui.screens.homeScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.viewModels.HomeViewModel


@Composable
fun Search(homeViewModel: HomeViewModel){

    val query by homeViewModel.query.collectAsState()

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
    ){
        OutlinedTextField(

            placeholder = { Text("Search",  color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = 50.dp)
                .padding(10.dp),
            singleLine = true,
            value = query,
            onValueChange = {homeViewModel.updateQuery(it)},
            textStyle = TextStyle(fontSize =  20.sp, color = Color.White),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor= Color(0xff16a085), // цвет при получении фокуса
                unfocusedBorderColor = Color(0xffcccccc)  // цвет при отсутствии фокуса

            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}