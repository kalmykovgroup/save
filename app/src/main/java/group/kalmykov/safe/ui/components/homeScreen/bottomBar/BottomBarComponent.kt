package group.kalmykov.safe.ui.components.homeScreen.bottomBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.models.Source_
import group.kalmykov.safe.ui.components.common.CommonBasicTextField
import group.kalmykov.safe.viewModels.HomeViewModel


class BottomBarComponent(private val homeViewModel: HomeViewModel) : ViewModel(){



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BasicTextFieldBlock(name: String, setName : (String) -> Unit){

        CommonBasicTextField(name, setName, "source name") {
            val isFocused by it.collectIsFocusedAsState()

            LaunchedEffect(isFocused) {
                if (isFocused) setName("")
            }

        }

    }

    @Composable
    fun DialogEnterNameSource(cancel: () -> Unit, save: (String) -> Unit){
        var name by remember { mutableStateOf("unknown") }

        AlertDialog(
            onDismissRequest = {cancel()},
            text = {
                BasicTextFieldBlock(name) { name = it }
            },
            confirmButton = {
                Button({save(name); cancel();}) {
                    Text("Создать", fontSize = 22.sp)
                }
            }
        )
    }



    @Composable
    fun Show(){

        var isOpenDialog by remember { mutableStateOf(false) }

        if(isOpenDialog){
            DialogEnterNameSource({isOpenDialog = false}, {
                homeViewModel.homeScreen.listSourcesComponent.sources.add(Source_(it))
            })
        }

        Row(modifier = Modifier.fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray)
            .padding(10.dp)
            .height(50.dp),
            horizontalArrangement = Arrangement.End){
            Box(modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()){
                Button(onClick = {isOpenDialog = true},
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors( containerColor = colorResource(R.color.plus_large_color_container))
                ){


                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.plus_large),
                        contentDescription = "*",
                        modifier = Modifier.size(20.dp, 20.dp),
                        colorFilter = ColorFilter.tint(colorResource(R.color.plus_large_color_svg))
                    )
                }
            }

        }
    }
}

