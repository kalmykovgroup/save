package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.inputs.DescriptionField
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.inputs.PasswordField
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.inputs.SourceField
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.inputs.UsernameField
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun Content(source: Source, homeViewModel: HomeViewModel){


            Column(modifier = Modifier.fillMaxWidth()
                .background(color = colorResource(R.color.background_active_source_container))
                .padding(start = 5.dp, top = 0.dp, end = 5.dp)
            ) {
               SourceField(source)
               UsernameField(source)
               PasswordField(source)
               DescriptionField(source)
               BottomButtons(source, homeViewModel)
            }

}