package group.kalmykov.safe.ui.screens.homeScreen.components.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.SourceItem
import group.kalmykov.safe.viewModels.HomeViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.paging.LoadState
import androidx.paging.compose.*
import group.kalmykov.safe.data.entity.Source
import kotlinx.coroutines.flow.Flow


@Composable
fun ListSources(homeViewModel: HomeViewModel) {
    //val lazyPagingItems = homeViewModel.sources.collectAsLazyPagingItems()

    val lazyPagingItems = homeViewModel.items.collectAsLazyPagingItems()

    val listState = rememberLazyListState()
    val itemHeight = 50.dp
    var availableHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val sortOrder by homeViewModel.sortOrder.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            availableHeight = with(density) { it.size.height.toDp() }
        }
    ) {
        when (val refreshState = lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                // Отображение индикатора загрузки при начальной загрузке данных
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                // Отображение сообщения об ошибке при неудачной начальной загрузке
                val error = refreshState.error
                Text(
                    text = "Ошибка: ${error.message}",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
            is LoadState.NotLoading -> {
                // Отображение списка после загрузки данных
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {

                    items(count = lazyPagingItems.itemCount, key = lazyPagingItems.itemKey { it.id }) { index ->

                        val item = lazyPagingItems[index]

                        if (item != null) {

                            ItemRow(item = item,
                                zIndex = lazyPagingItems.itemCount - index.toFloat(),
                                itemHeight = itemHeight,
                                homeViewModel = homeViewModel)

                        } else {
                            Box(modifier = Modifier.fillMaxWidth().height(itemHeight), contentAlignment = Alignment.Center){
                                // Отображение состояния загрузки
                                CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
                            }
                        }

                    }

                    item { // Добавлен элемент в конце
                        Spacer(modifier = Modifier.height(availableHeight - itemHeight).fillMaxWidth())
                    }

                    // Обработка состояния загрузки при добавлении данных
                    if (lazyPagingItems.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else if (lazyPagingItems.loadState.append is LoadState.Error) {
                        item {
                            val error = (lazyPagingItems.loadState.append as LoadState.Error).error
                            Text(
                                text = "Ошибка: ${error.message}",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ItemRow(item: Source, zIndex: Float, itemHeight: Dp, homeViewModel: HomeViewModel) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = item) {
        visible = true
    }

    AnimatedVisibility(
        modifier = Modifier.zIndex(zIndex),
        visible = visible,
        exit = fadeOut() + shrinkVertically(),
        content = {
            SourceItem(
                source = item,
                zIndex = zIndex,
                height = itemHeight,
                homeViewModel = homeViewModel
            )
        }
    )
}
