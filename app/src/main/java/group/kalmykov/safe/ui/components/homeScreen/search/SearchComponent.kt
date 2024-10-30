package group.kalmykov.safe.ui.components.homeScreen.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.viewModels.HomeViewModel




class SearchComponent() : ViewModel(){

    private var search by mutableStateOf("")

    private fun updateSearch(input: String) {
        search = input

    }

    @Composable
    fun Show(){
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
        ){
            OutlinedTextField(

                placeholder = {Text("Search")},
                modifier = Modifier.fillMaxWidth()
                    .heightIn(min = 50.dp)
                    .padding(10.dp),
                singleLine = true,
                value = search,
                onValueChange = { value -> updateSearch(value) },
                textStyle = TextStyle(fontSize =  20.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor= Color(0xff16a085), // цвет при получении фокуса
                    unfocusedBorderColor = Color(0xffcccccc)  // цвет при отсутствии фокуса
                ),
            )
        }
    }

    class SearchComponentPreviewParameterProvider : PreviewParameterProvider<HomeViewModel> {
        override val values = sequenceOf(
            HomeViewModel()
        )
    }

    @Preview(showBackground = true, device = "id:Nexus S")
    @Composable
    fun GreetingPreview(@PreviewParameter(SearchComponentPreviewParameterProvider::class) homeViewModel: HomeViewModel) {
       Show()
    }

}