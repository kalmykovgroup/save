package group.kalmykov.safe.ui.screens.homeScreen.components.dialog
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import group.kalmykov.safe.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import group.kalmykov.safe.ui.components.SwipeState
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SwipeToDismissBox(close: () -> Unit, content: @Composable () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val maxOffsetPx = with(LocalDensity.current) { screenWidth.toPx() }


    // Определяем якоря (anchors) для состояний
    val anchors = mapOf(
        0f to SwipeState.Expanded,
        -maxOffsetPx to SwipeState.Collapsed
    )
    var autoClose by remember { mutableStateOf(false) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val maxOffsetX = -maxOffsetPx

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .draggable(
                state = rememberDraggableState { delta ->
                    scope.launch {
                        val newValue = (offsetX.value + delta).coerceIn(maxOffsetX, 0f)
                        offsetX.snapTo(newValue)
                    }
                },
                orientation = Orientation.Horizontal,

            )
    ) {
        content()
    }

    if(autoClose){
        scope.launch {
            offsetX.animateTo(
                targetValue = maxOffsetX,
                animationSpec = tween(300)

            )
        }
    }

    LaunchedEffect(offsetX.value) {


        if (offsetX.value <= maxOffsetX * 0.3f) {
            autoClose = true
           /* scope.launch {
                offsetX.animateTo(
                    targetValue = maxOffsetX,
                    animationSpec = tween(300)
                )
            }*/
            scope.launch{
                delay(300)
              //  autoClose = false
              //  close()
            }
        } else if (offsetX.value != 0f) {
            /*scope.launch {
                offsetX.animateTo(0f, animationSpec = tween(300))
            }*/
        }
    }
}


@Composable
fun MyCustomDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {

    val coroutine = rememberCoroutineScope()

    var dialogVisible by remember { mutableStateOf(false) }

    var contentBounds by remember { mutableStateOf<Rect?>(null) }


    LaunchedEffect(Unit) {
        dialogVisible = true
    }

        Dialog(
            onDismissRequest = {
                dialogVisible = false
                onDismissRequest()
            },
            properties = properties.let {
                DialogProperties(
                    dismissOnBackPress = it.dismissOnBackPress,
                    dismissOnClickOutside = it.dismissOnClickOutside,
                    securePolicy = it.securePolicy,
                    usePlatformDefaultWidth = false
                )
            },
            content = {
                SwipeToDismissBox(close = {
                    dialogVisible = false
                    onDismissRequest()
                }){
                    Box(modifier = Modifier.fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                // Проверяем, попал ли клик в область, не занятую контентом
                                val bounds = contentBounds
                                if (bounds == null || !bounds.contains(offset)) {
                                    dialogVisible = false
                                    coroutine.launch {
                                        delay(200)
                                        onDismissRequest()
                                    }
                                }
                            }
                        }
                        , contentAlignment = Alignment.Center){

                        Box( modifier = Modifier
                            .onGloballyPositioned { layoutCoordinates ->
                                val position = layoutCoordinates.positionInParent()
                                val size = layoutCoordinates.size.toSize()
                                contentBounds = Rect(position, size)
                            }){
                            AnimatedVisibility(
                                visible = dialogVisible,
                                enter = slideInHorizontally(
                                    initialOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = tween(durationMillis = 200)
                                ),
                                exit = slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = tween(durationMillis = 200)
                                )
                            ) {
                                Surface(
                                    color = Color.Transparent,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxWidth()
                                        .clip(shape = RoundedCornerShape(10.dp))
                                        .background(color = colorResource(R.color.backgroundModelAddSource))
                                        .padding(5.dp) ,
                                    content = content
                                )
                            }
                        }



                    }
                }


            }
        )






}