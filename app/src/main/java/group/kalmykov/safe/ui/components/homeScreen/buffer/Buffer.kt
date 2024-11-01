package group.kalmykov.safe.ui.components.homeScreen.buffer

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R

@Composable
fun Buffer(){

    val clipBoardText by rememberClipboardText()

    var isShowBuffer by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().height(20.dp), contentAlignment = Alignment.CenterStart){
        Row(verticalAlignment = Alignment.CenterVertically){
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically){
                Text(text = "buffer: ", color = colorResource(R.color.composable), style = TextStyle(fontSize = 10.sp))

                BasicTextField(
                    value = clipBoardText.toString().ifEmpty { "" },
                    singleLine = true,
                    onValueChange = {},
                    textStyle = TextStyle(color = colorResource(R.color.composable), fontSize = 10.sp),
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().background(color = Color.Transparent),
                    visualTransformation = if (isShowBuffer) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }


            Box(modifier = Modifier
                .padding(start = 5.dp)
                .width(30.dp)
                .fillMaxHeight()
                .background(color = colorResource(R.color.background_btn_attribute))
                .clickable(onClick = {
                    isShowBuffer = !isShowBuffer
                })
                , contentAlignment = Alignment.Center
            ){
                Image(
                    imageVector = ImageVector.vectorResource(if (isShowBuffer) R.drawable.eye_visible else R.drawable.eye_hide),
                    contentDescription = "?",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(29.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.icon_eye)),
                )
            }


        }

    }
}


@Composable
fun rememberClipboardText(): MutableState<AnnotatedString?> {
    val clipboardManager = LocalClipboardManager.current
    val text = remember { mutableStateOf(clipboardManager.getText()) }
    onClipDataChanged {
        text.value = clipboardManager.getText()
    }

    LaunchedEffect(Unit){
        text.value = clipboardManager.getText()
    }

    return text
}

@SuppressLint("ComposableNaming")
@Composable
fun onClipDataChanged(onPrimaryClipChanged: ClipData?.() -> Unit) {
    val clipboardManager =
        LocalContext.current.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val callback = remember {
        ClipboardManager.OnPrimaryClipChangedListener {
            onPrimaryClipChanged(clipboardManager.primaryClip)
        }
    }
    DisposableEffect(clipboardManager) {
        clipboardManager.addPrimaryClipChangedListener(callback)
        onDispose {
            clipboardManager.removePrimaryClipChangedListener(callback)
        }
    }
}