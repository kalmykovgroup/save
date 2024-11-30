package group.kalmykov.safe.ui.screens.homeScreen.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun EditSourceDialog(visual: Boolean, setVisual: (Boolean) -> Unit, save: (Source) -> Unit, source: Source, homeViewModel: HomeViewModel) {
    if(visual){

        var visualDeleteSourceDialog: Boolean by remember { mutableStateOf(false) }

        DeleteItemDialog(
            visual = visualDeleteSourceDialog,
            setVisual = { visualDeleteSourceDialog = it },
            successClick = {
                homeViewModel.removeSource(source)
            }
        )

        SourceFormDialog(cancel = { setVisual(false) }, save = save, source = source, topBlock = {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                Box(modifier = Modifier.width(30.dp).height(30.dp).clip(shape = RoundedCornerShape(5.dp)).clickable{
                    visualDeleteSourceDialog = true
                }, contentAlignment = Alignment.Center){
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.delete_icon),
                        contentDescription = "del",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.height(18.dp),
                        colorFilter = ColorFilter.tint(colorResource(R.color.delete_icon_color)),
                    )
                }
            }
        })
    }
}