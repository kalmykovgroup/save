package group.kalmykov.safe.ui.screens.homeScreen.components.dialog

import androidx.compose.runtime.Composable
import group.kalmykov.safe.data.entity.Source

@Composable
fun AddSourceDialog(cancel: () -> Unit, save: (Source) -> Unit){
    SourceFormDialog(cancel = cancel, save = save)
}