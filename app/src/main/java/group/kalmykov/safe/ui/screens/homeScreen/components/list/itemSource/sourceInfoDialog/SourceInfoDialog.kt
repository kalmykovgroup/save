package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.sourceInfoDialog.components.Content
import group.kalmykov.safe.viewModels.HomeViewModel


@Composable
fun SourceInfoDialog(source: Source, visibility: MutableState<Boolean>, homeViewModel: HomeViewModel){

    if(visibility.value){
        Dialog(
            onDismissRequest = { visibility.value = false },
            properties = DialogProperties().let {
                DialogProperties(
                    dismissOnBackPress = it.dismissOnBackPress,
                    dismissOnClickOutside = it.dismissOnClickOutside,
                    securePolicy = it.securePolicy,
                    usePlatformDefaultWidth = false
                )
            },
            content = {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .padding(start = 0.dp, end = 40.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = colorResource(R.color.backgroundModelAddSource))
                        .padding(0.dp, 15.dp),
                    content = {  Content(source, homeViewModel) }
                )
            }
        )
    }
}