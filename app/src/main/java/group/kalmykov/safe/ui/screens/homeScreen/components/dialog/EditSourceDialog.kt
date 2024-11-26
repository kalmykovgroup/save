package group.kalmykov.safe.ui.screens.homeScreen.components.dialog

import androidx.compose.runtime.Composable
import group.kalmykov.safe.data.entity.Source

@Composable
fun EditSourceDialog(visual: Boolean, setVisual: (Boolean) -> Unit, save: (Source) -> Unit, source: Source) {
    if(visual){
        SourceFormDialog(cancel = { setVisual(false) }, save = save, source = source)
    }
}