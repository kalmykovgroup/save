package group.kalmykov.safe.ui.components.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.viewModels.LoginViewModel


@Composable
fun InputBoxes(loginViewModel : LoginViewModel){
    LazyRow(Modifier.padding(10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        items(loginViewModel.passBoxArray) { item ->

            var color = colorResource(R.color.pass_default)

            if (loginViewModel.isSuccess == true){
                color = colorResource(R.color.pass_green_success)
            }else if(loginViewModel.isSuccess == false){
                color = colorResource(R.color.pass_red_error)
            }

            Box(modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .padding(3.dp)
                .clip(RoundedCornerShape(5.dp))
                .border(width = 1.dp, color = color, shape= RoundedCornerShape(5.dp))
                .background(Color.LightGray)
                .padding(5.dp)
                ,

                contentAlignment = Alignment.Center
            ) {

                if(item != null){
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.pass_icon),
                        contentDescription = "*",
                        modifier = Modifier.size(20.dp, 20.dp),
                        colorFilter = ColorFilter.tint(color)
                    )
                }

            }

        }
    }
}

