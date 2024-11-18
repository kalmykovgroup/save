package group.kalmykov.safe.ui.components.homeScreen.listSources

import androidx.compose.runtime.Composable
import group.kalmykov.safe.entity.Source

@Composable
fun EditSourceDialog(cancel: () -> Unit, save: (Source) -> Unit, source: Source) {
    SourceFormDialog(cancel = cancel, save = save, source = source)
}