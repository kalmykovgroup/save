package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import group.kalmykov.safe.R

import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.screens.homeScreen.components.dialog.EditSourceDialog
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.components.SourceCheckbox
import group.kalmykov.safe.viewModels.HomeViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun SourceItem(source: Source, zIndex: Float, height: Dp, homeViewModel: HomeViewModel){

    //Если установлен режим массового выбора и выбран текущий source, (проверяем на то, что он находится в списке выбранных
    val isSelected = remember { mutableStateOf(homeViewModel.isSelectedMode && homeViewModel.listSelectedSources.any{source.id == it.id}) }
    val coroutineScope = rememberCoroutineScope()

    //Если открыт выезающий блок подробной информации
    val isOpen = remember { mutableStateOf(false) }

    EditSourceDialog(
        visual = isOpen.value,
        setVisual = { isOpen.value = it },
        save = { homeViewModel.updateSource(it) },
        source = source,
        homeViewModel = homeViewModel
    )

    Row(modifier = Modifier.zIndex(zIndex)){

        SourceCheckbox(source, isSelected, homeViewModel)

        Column{  Row(modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .height(height)
                .background(color = colorResource(R.color.background_item_source))
                .then(
                    when(isSelected.value){
                        true -> Modifier.border(width = 1.dp, color = colorResource(R.color.orange), shape = RoundedCornerShape(5.dp))
                        false -> Modifier
                    }
                )
                .pointerInput(source.id) {
                    detectTapGestures(
                        onTap = {
                            coroutineScope.launch {
                                when(homeViewModel.isSelectedMode){
                                    true -> { //Если было зажатие - происходит массовый выбор
                                        when(isSelected.value){//Если выбран убираем, если не выбран добовляем
                                            true -> homeViewModel.listSelectedSources.remove(source)
                                            false -> homeViewModel.listSelectedSources.add(source)
                                        }
                                        isSelected.value = !isSelected.value
                                    }
                                    false -> { //Простой клик по блоку
                                        when(isOpen.value){
                                            true -> { //Если открыт текущий и мы по текущему кликнули
                                                isOpen.value = false //Закрываем текущий.
                                                homeViewModel.closePrevOpenSource = null //Переводим в статус `открытых нет`
                                            }
                                            false -> { //Если текущий не открыт
                                                homeViewModel.closePrevOpenSource?.let { it() } //Закрываем предыдущий открытый если такой остался
                                                isOpen.value = true //Открываем текущий
                                                homeViewModel.closePrevOpenSource = { isOpen.value = false } //Создаем лямбду для закрытия текущего
                                            }
                                        }
                                    }
                                }
                            }

                        },
                        onLongPress = {
                            homeViewModel.listSelectedSources.clear() //Очищаем список массового выбота после предыдущего запроса
                            homeViewModel.listSelectedSources.add(source) //Добавляем текущий source
                           // homeViewModel.isSelectedMode = true //Переводим в режим массового выбора не нужно, так как это свойство вычислимое
                            isSelected.value = true //Помечаем текущий как выбранный
                        }
                    )
                }

                ,verticalAlignment = Alignment.CenterVertically){

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.dot),
                            contentDescription = ".",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.width(20.dp),
                            colorFilter = ColorFilter.tint(colorResource(R.color.dot_row_source))
                        )

                        Text(text = source.host, style = TextStyle(fontSize = 17.sp, color = colorResource(R.color.color_source_name)))
                    }
                }


                Box(modifier = Modifier.width(50.dp)
                    , contentAlignment = Alignment.CenterEnd){
                    Row(modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            color = colorResource(
                                when(isOpen.value){
                                    true -> R.color.background_ellipsis_active
                                    false ->  R.color.background_ellipsis
                                }
                            )
                        )
                        .padding(
                            start = (if (isOpen.value) 5.dp else 3.dp),
                            top = 2.dp,
                            end = (if (isOpen.value) 5.dp else 3.dp),
                            bottom = 4.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        val color = when(isSelected.value){
                            true -> colorResource(R.color.orange)
                            false -> when(isOpen.value){
                                true -> Color.White
                                else -> colorResource(R.color.color_ellipsis)
                            }
                        }

                        Text(textAlign = TextAlign.Center, text = "{", color = color)

                        if(!isOpen.value){
                            Text(textAlign = TextAlign.Center, text = " . . . ", fontSize = 17.sp, color = color)
                            Text(textAlign = TextAlign.Center, text = "}", fontSize = 17.sp, color = color)
                        }
                    }
                }
            }


            HorizontalDivider(thickness = 1.dp, color = colorResource(R.color.divider_row_source))
        }
    }
}