package group.kalmykov.safe.ui.components.homeScreen.listSources

import androidx.compose.runtime.Composable
import group.kalmykov.safe.models.Source

@Composable
fun AddSourceModal(cancel: () -> Unit, save: (Source) -> Unit){
    SourceFormModal(cancel = cancel, save = save)
}