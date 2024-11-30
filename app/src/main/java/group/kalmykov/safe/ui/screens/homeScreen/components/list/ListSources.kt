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
import androidx.compose.ui.geometry.Rect


@Composable
fun ListSources(homeViewModel: HomeViewModel) {
    //val lazyPagingItems = homeViewModel.sources.collectAsLazyPagingItems()

    val sources = homeViewModel.sources.collectAsState()

    val listState = rememberLazyListState()
    val itemHeight = 50.dp
    var availableHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val scrollToIndex by homeViewModel.scrollToIndex.collectAsState()

    Box(modifier = Modifier
        .onGloballyPositioned {
            availableHeight = with(density) { it.size.height.toDp() }
        }
    ) {
        LazyColumn(
            state = listState
        ) {

            itemsIndexed(
                items = sources.value,
                key = {_, source, -> source.id }
            ) { index, source ->

                SourceItem(
                    source = source,
                    zIndex = sources.value.size - index.toFloat(),
                    height = itemHeight,
                    homeViewModel = homeViewModel
                )

            }

            item { // Добавлен элемент в конце
                Spacer(modifier = Modifier.height(availableHeight - itemHeight).fillMaxWidth())
            }


        }


        // Наблюдаем за изменением scrollToIndex и выполняем прокрутку
        LaunchedEffect(scrollToIndex) {
            scrollToIndex?.let { index ->
                if (index in sources.value.indices) {
                    listState.animateScrollToItem(index)
                    homeViewModel.onScrollCompleted()
                }
            }
        }
    }

}


