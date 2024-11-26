/*
package group.kalmykov.safe.ui.components.homeScreen.listSources

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import group.kalmykov.safe.R
import group.kalmykov.safe.entity.Source

@Composable
fun SourceItem(source: Source, zIndex: Float, parent: ListSources) {

    var isSelectedCurrentItem by remember { mutableStateOf(parent.isSelectedSources && parent.listIdSelectedSources.any{source.id == it}) }

    val targetWidth = if (isSelectedCurrentItem) 30.dp else 0.dp
    val animatedWidth by animateDpAsState(targetValue = targetWidth, animationSpec = tween(300), label = "")


    Row(modifier = Modifier.zIndex(zIndex)){

        AnimatedVisibility(
            visible = isSelectedCurrentItem,
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
                    checked = isSelectedCurrentItem,
                    onCheckedChange = {
                        if(parent.listIdSelectedSources.any{id -> id == source.id}){
                            parent.listIdSelectedSources.remove(source.id)
                            isSelectedCurrentItem = false
                        }else{
                            parent.listIdSelectedSources.add(source.id)
                            isSelectedCurrentItem = true
                        }
                    }
                )
            }
        }

        var isSourceOpen by remember { mutableStateOf(false) }

        isSourceOpen = parent.openSource?.id == source.id

        Column{

            Box(modifier = Modifier.fillMaxWidth().zIndex(1f)){
                Row(modifier = Modifier
                    .then(
                        if (isSelectedCurrentItem) {
                            Modifier.border(width = 1.dp,color = colorResource(R.color.orange),shape = RoundedCornerShape(5.dp))
                        } else {
                            Modifier
                        }
                    )
                    .zIndex(1f)
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.background_item_source))
                    .pointerInput(source.id) {
                        detectTapGestures(
                            onTap = {
                                if(parent.isSelectedSources){ //При нажатии на ресурс мы проверяем в каком режиме мы, в массовом выделении или это просто клик на открытие

                                    if(isSelectedCurrentItem)
                                        parent.listIdSelectedSources.remove(source.id)
                                    else
                                         parent.listIdSelectedSources.add(source.id)
                                    isSelectedCurrentItem = !isSelectedCurrentItem

                                }else{
                                    if (isSourceOpen)
                                        parent.openSource = null
                                    else{
                                        parent.openSource = source
                                        dragNewHeightPx = 0f
                                    }
                                }
                            },
                            onLongPress = {
                                parent.listIdSelectedSources.clear()
                                parent.listIdSelectedSources.add(source.id)
                                parent.isSelectedSources = true
                                isSelectedCurrentItem = true
                            }
                        )
                    }
                    .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart){
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.dot),
                                contentDescription = ".",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.width(20.dp),
                                colorFilter = ColorFilter.tint(colorResource(R.color.dot_row_source))
                            )

                            Text(text = source.host, style = TextStyle(fontSize = 17.sp, color = colorResource(
                                R.color.color_source_name)
                            )
                            )
                        }
                    }


                    Box(modifier = Modifier
                        .width(50.dp), contentAlignment = Alignment.CenterEnd){
                        Row(modifier = Modifier
                            .fillMaxHeight()
                            .background(
                                color = colorResource(
                                    if (isSourceOpen)
                                        R.color.background_ellipsis_active
                                    else
                                        R.color.background_ellipsis
                                )
                            )
                            .padding(
                                start = (if (isSourceOpen) 5.dp else 3.dp),
                                top = 2.dp,
                                end = (if (isSourceOpen) 5.dp else 3.dp),
                                bottom = 4.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            val color = if (isSelectedCurrentItem) {
                                colorResource(R.color.orange)
                            } else {
                                if(isSourceOpen){
                                    Color.White
                                }else
                                    colorResource(R.color.color_ellipsis)
                            }

                            Text(textAlign = TextAlign.Center, text = "{", color = color)

                            if(!isSourceOpen){
                                Text(textAlign = TextAlign.Center, text = " . . . ", fontSize = 17.sp, color = color)
                                Text(textAlign = TextAlign.Center, text = "}", fontSize = 17.sp, color = color)
                            }

                        }

                    }
                }
            }

            SourceDataContainer(source, isSourceOpen, parent)

            HorizontalDivider(thickness = 1.dp, color = colorResource(R.color.divider_row_source))
        }
    }
}

*/
