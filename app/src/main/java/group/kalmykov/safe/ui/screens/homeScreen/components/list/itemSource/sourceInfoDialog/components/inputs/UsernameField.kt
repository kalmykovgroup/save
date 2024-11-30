package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.BtnCopy
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.InputContainer

@Composable
fun UsernameField(source: Source){
    InputContainer(label = "Username", text = source.username?: "", attributes = {
        BtnCopy(value = source.username)
    })
}