package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.viewModels.HomeViewModel

@Composable
fun SourceCheckbox(source: Source, isSelected: MutableState<Boolean>, homeViewModel: HomeViewModel){

    val targetWidth = if (isSelected.value) 30.dp else 0.dp
    val animatedWidth by animateDpAsState(targetValue = targetWidth, animationSpec = tween(300), label = "")


    AnimatedVisibility(
        visible = isSelected.value,
        enter = fadeIn(animationSpec = tween(300)) + scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(300)
        ),
        exit = fadeOut(animationSpec = tween(300)) + scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(300)
        )

    ){
        Box(modifier = Modifier.width(animatedWidth).fillMaxHeight().clickable {}){
            Checkbox(
                checked = isSelected.value,
                onCheckedChange = {

                    when(isSelected.value){
                        true ->  homeViewModel.listSelectedSources.remove(source)
                        false ->  homeViewModel.listSelectedSources.add(source)
                    }
                    isSelected.value = !isSelected.value
                }
            )
        }
    }
}
