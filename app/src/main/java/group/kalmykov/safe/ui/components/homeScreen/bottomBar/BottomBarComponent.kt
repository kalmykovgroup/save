package group.kalmykov.safe.ui.components.homeScreen.bottomBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.models.Source
import group.kalmykov.safe.viewModels.HomeViewModel


class BottomBarComponent(private val homeViewModel: HomeViewModel) : ViewModel(){

    private fun addPassword(){
        homeViewModel.homeScreen.sourceListComponent.sources.add(Source())
    }

    @Composable
    fun Show(){
        Row(modifier = Modifier.fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray)
            .padding(10.dp)
            .height(50.dp),
            horizontalArrangement = Arrangement.End){
            Box(modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()){
                Button(onClick = {addPassword()},
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

