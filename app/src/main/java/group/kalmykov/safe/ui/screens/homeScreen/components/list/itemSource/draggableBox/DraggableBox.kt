package group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.viewModels.HomeViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import group.kalmykov.safe.ui.screens.homeScreen.components.list.itemSource.draggableBox.components.Content
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@SuppressLint("UseOfNonLambdaOffsetOverload", "UnrememberedMutableState",
    "CoroutineCreationDuringComposition", "SuspiciousIndentation"
)
@Composable
fun DraggableBox(source: Source, isOpen: MutableState<Boolean>, homeViewModel: HomeViewModel) {


    var heightIntParentBlock by remember { mutableStateOf<Int?>(null) }

    //Величина, которую мы получаем когда используем drag
    var offsetFloatY by remember { mutableFloatStateOf(0f) }

    var isStartDrag by remember { mutableStateOf(false) }

    //Вычисляем высоту занимаемого контентом
    val layoutHeight = remember(isOpen, heightIntParentBlock) {
        derivedStateOf {
            if(isOpen.value && heightIntParentBlock != null){
                (heightIntParentBlock!! + offsetFloatY.toInt())
                    .coerceAtLeast(0)
                    .coerceAtMost(heightIntParentBlock!!)
            }else 0
        }
    }

    val animatedStateHeight by animateIntAsState(
        targetValue = layoutHeight.value,
        animationSpec = tween(durationMillis = if(isStartDrag) 0 else 500), label = "",
        finishedListener = { }
    )

        //Layout нам нужен для подсчета высоты занимаемого контента
        Layout(modifier = Modifier.zIndex(-1f), content = {
            Box(modifier = Modifier.pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isStartDrag = true },
                    onDragEnd = {
                        isStartDrag = false
                        if(offsetFloatY < 0){
                            isOpen.value = false
                        }
                        offsetFloatY = 0f

                    },
                    onDrag = { change, _ ->
                        val dragAmount = change.positionChange().y
                        offsetFloatY += dragAmount
                        if (change.positionChange() != Offset.Zero) change.consume()
                    }
                )
            }){
                Content(source, homeViewModel)
            }
        }) { measurables, constraints ->
            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

           val listPlaceable = measurables.map { measurable -> measurable.measure(looseConstraints)}

            if(heightIntParentBlock == null){
                heightIntParentBlock = listPlaceable[0].height
            }

            layout(width = constraints.maxWidth, height = animatedStateHeight) {
                listPlaceable.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = animatedStateHeight - placeable.height)
                }
            }
        }




}

@Composable
fun OverlappingBoxes() {

    val density = LocalDensity.current

    val isOpen = remember { mutableStateOf(false) }

    val contentHeight = remember { mutableFloatStateOf(0f) }
    val offsetY = remember { Animatable(-contentHeight.floatValue) }

    // Обновляем offsetY при изменении isOpen или contentHeight
    LaunchedEffect(isOpen.value, contentHeight.floatValue) {
        if (contentHeight.floatValue == 0f) return@LaunchedEffect
        if (isOpen.value) {
            // Открытие: анимируем offsetY от отрицательной высоты до 0
            offsetY.snapTo(-contentHeight.floatValue)
            offsetY.animateTo(0f, animationSpec = tween(500))
        } else {
            // Закрытие: анимируем offsetY от текущего положения до отрицательной высоты
            offsetY.animateTo(-contentHeight.floatValue, animationSpec = tween(500))
        }
    }

    ConstraintLayout(modifier = Modifier
        .height(50.dp)
        .fillMaxWidth()
        .zIndex(3f)
        .border(1.dp, Color.Red)) {
        val (contentRef, overlayRef) = createRefs()

        // Основной контент
        Box(modifier = Modifier
            .constrainAs(contentRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .background(Color.Blue)
            .clickable { isOpen.value = !isOpen.value }
            .fillMaxWidth()
            .height(50.dp) // Размер внешнего Box
            .fillMaxWidth()
        ) {
            Row {
                Text(text = "contentHeight: ${contentHeight.floatValue}", color = Color.Red, modifier = Modifier.zIndex(2f))
                Text(text = "offsetY: ${offsetY.value}", color = Color.Red, modifier = Modifier.zIndex(2f))
                Text(text = "contentHeightDp: ${ with(density) { 375.toDp() }}", color = Color.Red, modifier = Modifier.zIndex(1f))
            }
        }

        Box(
            modifier = Modifier
                .constrainAs(overlayRef) {
                    top.linkTo(contentRef.bottom) // Наложение на нижнюю границу контента
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.value(200.dp)
                }.zIndex(5f)
                .fillMaxWidth()
                .background(Color.LightGray)
            //.offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }

        ) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp).onGloballyPositioned { coordinates ->
                val height = coordinates.size.height.toFloat()
                if (height != contentHeight.floatValue) {
                    contentHeight.floatValue = height
                }
            }
            ){

            }
        }
    }
}


@Composable
fun DraggableBox7(
    source: Source,
    isOpen: MutableState<Boolean>,
    homeViewModel: HomeViewModel
) {
    val contentHeight = remember { mutableStateOf(0f) }
    val offsetY = remember { Animatable(-contentHeight.value) }
    val coroutineScope = rememberCoroutineScope()

    // Обновляем offsetY при изменении isOpen или contentHeight
    LaunchedEffect(isOpen.value, contentHeight.value) {
        if (contentHeight.value == 0f) return@LaunchedEffect
        if (isOpen.value) {
            // Открытие: анимируем offsetY от отрицательной высоты до 0
            offsetY.snapTo(-contentHeight.value)
            offsetY.animateTo(0f, animationSpec = tween(500))
        } else {
            // Закрытие: анимируем offsetY от текущего положения до отрицательной высоты
            offsetY.animateTo(-contentHeight.value, animationSpec = tween(500))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                val height = coordinates.size.height.toFloat()
                if (height != contentHeight.value) {
                    contentHeight.value = height
                }
            }
            .shadow(4.dp)
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        val newOffset = (offsetY.value + delta).coerceIn(-contentHeight.value, 0f)
                        offsetY.snapTo(newOffset)
                    }
                },
                onDragStopped = {
                    coroutineScope.launch {
                        val threshold = -contentHeight.value / 2
                        if (offsetY.value < threshold) {
                            // Закрываем, если смещение меньше половины отрицательной высоты
                            isOpen.value = false
                            offsetY.animateTo(-contentHeight.value, animationSpec = tween(300))
                        } else {
                            // Оставляем открытым (возвращаем вниз)
                            isOpen.value = true
                            offsetY.animateTo(0f, animationSpec = tween(300))
                        }
                    }
                }
            )
    ) {
        Content(source, homeViewModel)
    }
}
