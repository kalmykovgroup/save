package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.inputs

import androidx.compose.runtime.Composable
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.BtnCopy
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.InputContainer

@Composable
fun SourceField(source: Source){
    InputContainer(label = "Host", text = source.host, attributes = {
        BtnCopy(value = source.host)
    })
}