package group.kalmykov.safe.ui.screens.homeScreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import group.kalmykov.safe.ui.screens.homeScreen.components.dialog.AddSourceDialog
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun FloatingActionButtonContainer(homeViewModel: HomeViewModel){

    var isOpenDialogAddSource by remember { mutableStateOf(false) }

    if(isOpenDialogAddSource){
        AddSourceDialog({ isOpenDialogAddSource = false },
            save  = {
                homeViewModel.addSource(it)
                homeViewModel.scrollToIndex(0)
            }
        )

    }

    if(!homeViewModel.isSelectedMode) {
        FloatingActionButton(onClick = { isOpenDialogAddSource = true}) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}