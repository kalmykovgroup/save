package group.kalmykov.safe.ui.components.homeScreen.listSources

import androidx.compose.runtime.Composable
import group.kalmykov.safe.entity.Source

@Composable
fun EditSourceModal(cancel: () -> Unit, save: (Source) -> Unit, source: Source) {
    SourceFormModal(cancel = cancel, save = save, source = source)
}