package group.kalmykov.safe.ui.components.homeScreen.listSources

import androidx.compose.runtime.Composable
import group.kalmykov.safe.entity.Source

@Composable
fun AddSourceDialog(cancel: () -> Unit, save: (Source) -> Unit){
    SourceFormDialog(cancel = cancel, save = save)
}