package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.BtnCopy
import group.kalmykov.safe.ui.components.BtnPassVisible
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.footer.BottomButtons
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.footer.BottomFrame
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.inputs.DescriptionField
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.inputs.PasswordField
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.inputs.UsernameField
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun Content(source: Source, homeViewModel: HomeViewModel){

      Column {
            Column(modifier = Modifier.fillMaxWidth()
                .background(color = colorResource(R.color.background_active_source_container))
                .padding(start = 10.dp, top = 0.dp, end = 10.dp)
            ) {

               UsernameField(source)
               PasswordField(source)
               DescriptionField(source)
               BottomButtons(source, homeViewModel)
            }

            BottomFrame(homeViewModel)
        }

}