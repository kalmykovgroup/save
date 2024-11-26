package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.footer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun BottomFrame(homeViewModel: HomeViewModel){
    Column(modifier = Modifier
        .background(colorResource(R.color.background_home_screen))
        .clickable { homeViewModel.closeOpenSources()}
    ){
        Box(
            modifier = Modifier
                .width(30.dp)
                .background(color = colorResource(R.color.background_active_source_container)),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(text = "}", color = colorResource(R.color.color_ellipsis_active))
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
            .background(Color.Gray),
            contentAlignment = Alignment.Center)
        {
            Box(contentAlignment = Alignment.Center, modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()){
                Column {
                    HorizontalDivider(color = Color.Black, thickness = 2.dp)
                    Spacer(Modifier.height(5.dp))
                    HorizontalDivider(color = Color.Black, thickness = 2.dp)
                    Spacer(Modifier.height(5.dp))
                    HorizontalDivider(color = Color.Black, thickness = 2.dp)
                }
            }

        }
    }
}