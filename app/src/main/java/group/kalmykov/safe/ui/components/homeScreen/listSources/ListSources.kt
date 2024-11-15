package group.kalmykov.safe.ui.components.homeScreen.listSources

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.R
import group.kalmykov.safe.functions.vibrator
import group.kalmykov.safe.entity.Source
import group.kalmykov.safe.ui.components.homeScreen.buffer.rememberClipboardText
import group.kalmykov.safe.ui.screens.HomeScreen


class ListSources(private val homeScreen: HomeScreen) : ViewModel() {

    private var activeSource : Source? by mutableStateOf(null)

    private var editSource: Source? by  mutableStateOf(null)

    private var deleteSource: Source? by  mutableStateOf(null)

    val selectedList: MutableList<Source> = mutableStateListOf()


    @Composable
    fun Show(){

        val infiniteTransition = rememberInfiniteTransition(label = "")

        val animatedColor by infiniteTransition.animateColor(
            initialValue = Color.Cyan,
            targetValue = Color.Magenta,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 12000
                    Color.Red at 0
                    Color.Magenta at 3000
                    Color.Blue at 6000
                    Color.Green at 9000
                    Color.Yellow at 12000
                },
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )


        val sources by homeScreen.homeViewModel.sourceRepository.sources.collectAsState()

        if(editSource != null){
            EditSourceModal(
                cancel = { editSource = null },
                save = {source ->
                    homeScreen.homeViewModel.sourceRepository.update(source)
                },
                editSource!!
            )
        }


        if (deleteSource != null) {
            AlertDialog(
                onDismissRequest = { deleteSource = null},
                title = { Text(text = "Подтверждение действия") },
                text = { Text("Вы действительно хотите удалить выбранный элемент?") },
                confirmButton = {
                    Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.End){
                        Button(
                            modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                            onClick = {deleteSource = null}
                        ) {
                            Text("Отменить", fontSize = 17.sp)
                        }

                        Button(
                            modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                            onClick = {
                                homeScreen.homeViewModel.sourceRepository.delete(deleteSource!!.id)
                                deleteSource = null
                            }
                        ) {
                            Text("Ok", fontSize = 17.sp)
                        }
                    }
                }
            )
        }

        LazyColumn(modifier = Modifier.then(
            if (selectedList.size > 0)
            Modifier.fillMaxHeight().clickable {
                selectedList.clear()
            }
            else Modifier)
           ) {
          itemsIndexed(sources, key = { _, item -> item.id }){ index, item ->
              SourceItemUi(source = item, zIndex = sources.size - index.toFloat(), selectAnimatedColor = animatedColor)
          }
        }

    }

    @Composable
    fun SourceItemUi(source: Source, zIndex: Float, selectAnimatedColor: Color){
        WrapperSourceItemUi(source = source, zIndex = zIndex, selectAnimatedColor = selectAnimatedColor)
    }

    @Composable
    fun WrapperSourceItemUi(source: Source, zIndex: Float, selectAnimatedColor: Color){

        var isSelectedCurrentItem by remember { mutableStateOf(selectedList.any{item -> item.id == source.id}) }

        isSelectedCurrentItem = selectedList.any{item -> item.id == source.id}

        Row(modifier = Modifier.zIndex(zIndex)){
            if(selectedList.size > 0){
                Box(modifier = Modifier.width(30.dp).fillMaxHeight().clickable {
                    selectSource(source)
                }){
                    Checkbox(
                        checked = isSelectedCurrentItem,
                        onCheckedChange = { selectSource(source) }
                    )
                }
            }

          SourceItemUiContent(source = source, isSelectedCurrentItem = isSelectedCurrentItem, selectAnimatedColor = selectAnimatedColor, onLongPress = {
              selectedList.clear()
              activeSource = null
              selectedList.add(source)
          })
        }
    }

    private fun selectSource(source: Source){
        if(selectedList.any{item -> item.id == source.id}){
            if(selectedList.size == 1){
                selectedList.clear()
            }else{
                selectedList.removeIf{item -> item.id == source.id}
            }
        }else{
            selectedList.add(source)
        }
    }

    @Composable
    fun SourceItemUiContent(source: Source, isSelectedCurrentItem: Boolean, selectAnimatedColor: Color,onLongPress: () -> Unit){
        var isSourceOpen by remember { mutableStateOf(false) }

        isSourceOpen = activeSource?.id == source.id

        Column{

           Box(modifier = Modifier.fillMaxWidth().zIndex(1f)){
               Row(modifier = Modifier
                   .then(
                       if (isSelectedCurrentItem) {
                           Modifier.graphicsLayer(shadowElevation = 20f) // "Светящийся" эффект с тенью
                               .border(
                                   width = 1.dp,
                                   color = selectAnimatedColor,
                                   shape = RoundedCornerShape(5.dp)
                               )

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
                               if(selectedList.size == 0){
                                   if (isSourceOpen){
                                       activeSource = null
                                   } else{
                                       activeSource = source
                                       dragNewHeightPx = 0f
                                   }
                               }else{
                                   selectSource(source)
                               }

                           },
                           onLongPress = {
                               onLongPress()
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

                           Text(text = source.host, style = TextStyle(fontSize = 17.sp, color = colorResource(R.color.color_source_name)))
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
                                selectAnimatedColor
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

            SourceContainerActiveAnimate(source, isSourceOpen)


            HorizontalDivider(thickness = 1.dp, color = colorResource(R.color.divider_row_source))
        }
    }

    @Composable
    fun SourceContainerActiveAnimate(source: Source, isOpenSource: Boolean){

        AnimatedVisibility(
            visible = isOpenSource,
            enter = expandVertically(expandFrom = Alignment.CenterVertically),
            exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            SourceContainerActive(source)
        }
    }


    private var isStartDrag by mutableStateOf(false)

    private var dragNewHeightPx by mutableFloatStateOf(0f)


    @Composable
    fun SourceContainerActive(source: Source){

        var originalColumnHeightPx by remember { mutableFloatStateOf(0f) }

        var passwordVisibility by  remember { mutableStateOf(false) }

        var offsetX by remember { mutableFloatStateOf(0f) }
        var offsetY by remember { mutableFloatStateOf(0f) }

        ContainerModalTop(modifier = Modifier, heightPx = if(isStartDrag || dragNewHeightPx.toInt() > 0 ) dragNewHeightPx.toInt() else null){

            Column(modifier = Modifier.onGloballyPositioned { coordinates ->
                // Set column height using the LayoutCoordinates
                originalColumnHeightPx = coordinates.size.height.toFloat()
            }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    offsetY = 0f
                                    isStartDrag = true //Говорим что нужно начать применять изменение размера блока
                                },
                                onDragEnd = { if(offsetY < 0) activeSource = null

                                    isStartDrag = false
                                    offsetY = 0f
                                },
                                onDragCancel = { },
                                onDrag = { change, offset ->
                                    change.consume()

                                    offsetY += offset.y

                                    var res = originalColumnHeightPx + offsetY;

                                    res = if(res < 0) 1F else res

                                    dragNewHeightPx = if(res > originalColumnHeightPx) originalColumnHeightPx else res
                                }
                            )
                        }
                ) {


                    Column {
                        Column(modifier = Modifier
                            .background(color = colorResource(R.color.background_active_source_container))
                            .padding(start = 10.dp, top = 0.dp, end = 10.dp)
                        ) {
                            InputContainer(label = "Username", inputField = {
                                BasicTextField(
                                    value = (source.username ?: ""),
                                    singleLine = true,
                                    onValueChange = {},
                                    textStyle = TextStyle(color = colorResource(R.color.color_value_field_resource)),
                                    readOnly = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.Transparent),
                                    //visualTransformation = visualTransformation,
                                    //  keyboardOptions = keyboardOptions
                                )
                            }, attributes = {
                                BtnCopy(value = source.username)
                            })

                            InputContainer(label = "Password", inputField = {
                                BasicTextField(
                                    value = source.password,
                                    singleLine = true,
                                    onValueChange = {},
                                    textStyle = TextStyle(color = colorResource(R.color.color_value_field_resource)),
                                    readOnly = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.Transparent),
                                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                                )
                            }, attributes = {
                                BtnShow(passwordVisibility) { passwordVisibility = it }
                                BtnCopy(value = source.password)
                            })

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .clip(shape = RoundedCornerShape(5.dp))
                                    .background(color = colorResource(R.color.background_field_resource))
                                    .padding(13.dp, 30.dp, 13.dp, 15.dp)
                            ) {

                                Text(
                                    text = "Description",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = colorResource(R.color.color_label_field_resource)
                                    ),
                                    modifier = Modifier
                                        .align(AbsoluteAlignment.TopLeft)
                                        .offset(y = (-20).dp)
                                )

                                BasicTextField(
                                    value = (if (source.description.isNullOrEmpty()) "Не примечаний" else source.description.toString()),
                                    singleLine = false,
                                    onValueChange = {},
                                    textStyle = TextStyle(color = colorResource(R.color.color_value_field_resource)),
                                    readOnly = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.Transparent)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp, 10.dp, 5.dp, 10.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = { editSource = source },
                                    content = { Text("Изменить") })
                                Spacer(modifier = Modifier.width(5.dp))
                                Button(
                                    onClick = { deleteSource = source },
                                    content = { Text("Удалить") }
                                )
                            }
                        }
                        Column(modifier = Modifier.clickable { activeSource = null }
                        ){
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .background(color = colorResource(R.color.background_active_source_container)),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(text = "}", color = colorResource(R.color.color_ellipsis_active))
                            }
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(25.dp)
                                .background(Color.Gray),
                                contentAlignment = Alignment.Center)
                            {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.width(100.dp).fillMaxHeight()){
                                    Column {
                                        HorizontalDivider(color = Color.Black, thickness = 2.dp)
                                        Spacer(Modifier.height(5.dp))
                                        HorizontalDivider(color = Color.Black, thickness = 2.dp)
                                        Spacer(Modifier.height(5.dp))
                                        HorizontalDivider(color = Color.Black, thickness = 2.dp)
                                    }
                                }

                            }
                        }
                    }

                }

            }
        }
    }

    @Composable
    fun ContainerModalTop(modifier: Modifier = Modifier, heightPx: Int? = null, content: @Composable () -> Unit) {

        val height: Int? = if(heightPx != null)
            if(heightPx > 0) heightPx else 0
        else null

        Layout(modifier = modifier.zIndex(-1f),content = content) { measurables, constraints ->

            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0, )
            var maxHeight = 0;

            val multiplyLambda = fun(measurable: Measurable, looseConstraints: Constraints,): Placeable {
                val value = measurable.measure(looseConstraints)
                maxHeight = Math.max(maxHeight, value.height)
                return value;
            }

            val listPlaceable = measurables.map { measurable ->
                multiplyLambda(measurable, looseConstraints)
            }

            val releaseHeight = height ?: maxHeight

            layout(width = constraints.maxWidth, height = releaseHeight) {

                listPlaceable.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = releaseHeight - placeable.height)
                }
            }
        }
    }


    @Composable
    fun BtnShow(passwordVisibility : Boolean, setPasswordVisibility : (Boolean) -> Unit){

        Box(modifier = Modifier
            .width(45.dp)
            .height(45.dp)
            .clip(shape = RoundedCornerShape(100))
            .background(color = colorResource(R.color.background_btn_attribute))
            .clickable(onClick = {
                setPasswordVisibility(!passwordVisibility)
            })
            , contentAlignment = Alignment.Center
        ){
            Image(
                imageVector = ImageVector.vectorResource(if (passwordVisibility) R.drawable.eye_visible else R.drawable.eye_hide),
                contentDescription = "?",
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(29.dp),
                colorFilter = ColorFilter.tint(colorResource(R.color.icon_eye)),
            )
        }
    }

    @Composable
    fun BtnCopy(value: String?){

        val clipBoardText by rememberClipboardText()
        val enabled = !value.isNullOrEmpty()
        val context = LocalContext.current

        Box(modifier = Modifier
            .padding(5.dp)
            .width(45.dp)
            .height(45.dp)
            .clip(shape = RoundedCornerShape(100))
            .clickable(onClick = {
                val clipboardManager =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboardManager.setPrimaryClip(ClipData.newPlainText("text", value))
                vibrator(context, milliseconds = 100)

            }, enabled = enabled && clipBoardText.toString() != value)
            , contentAlignment = Alignment.Center
        ){
            Image(
                imageVector = ImageVector.vectorResource(if(clipBoardText.toString() != value) R.drawable.copy else R.drawable.ok),
                contentDescription = "copy",
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(23.dp),
                colorFilter = ColorFilter.tint(colorResource(
                    if(enabled)
                        if(clipBoardText.toString() == value) R.color.ok
                        else R.color.btn_copy_active

                    else R.color.btn_copy_enactive)
                )
            )
        }
    }

    @Composable
    fun InputContainer(label: String, inputField : @Composable () -> Unit, attributes : @Composable () -> Unit){


        Row(modifier = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = colorResource(R.color.background_field_resource)),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(modifier = Modifier
                .weight(1f)
                .padding(13.dp, 3.dp)){

                Text(text = label ,
                    style = TextStyle( fontSize = 12.sp, color = colorResource(R.color.color_label_field_resource)),
                    modifier = Modifier
                        .align(AbsoluteAlignment.TopLeft)
                        .offset(y = (2).dp)
                )

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp), contentAlignment = Alignment.CenterStart){

                    inputField()


                }
            }
            attributes()

        }

    }


}