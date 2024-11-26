package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.footer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.screens.homeScreen.components.dialog.DeleteItemDialog
import group.kalmykov.safe.ui.screens.homeScreen.components.dialog.EditSourceDialog
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun BottomButtons(source: Source, homeViewModel: HomeViewModel) {


    var visualEditSourceModal: Boolean by remember { mutableStateOf(false) }

    var visualDeleteSourceDialog: Boolean by remember { mutableStateOf(false) }


    EditSourceDialog(
        visual = visualEditSourceModal,
        setVisual = { visualEditSourceModal = it },
        save = { homeViewModel.updateSource(it) },
        source = source
    )


    DeleteItemDialog(
        visual = visualDeleteSourceDialog,
        setVisual = { visualDeleteSourceDialog = it },
        successClick = {
            homeViewModel.removeSource(source)
        }
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 10.dp, 5.dp, 10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = { visualEditSourceModal = true },
            content = { Text("Изменить") })
        Spacer(modifier = Modifier.width(5.dp))
        Button(
            onClick = { visualDeleteSourceDialog = true },
            content = { Text("Удалить") }
        )
    }
}


