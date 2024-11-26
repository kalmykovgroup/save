package group.kalmykov.safe.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.ui.screens.homeScreen.components.buffer.rememberClipboardText

@Composable
fun BtnCopy(value: String?, modifier: Modifier = Modifier, colorIcon: Int? = null){


    val clipBoardText by rememberClipboardText()
    val enabled = !value.isNullOrEmpty()
    val context = LocalContext.current

    Box(modifier = modifier
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
            colorFilter = ColorFilter.tint(
                colorResource(
                    if(enabled)
                        if(clipBoardText.toString() == value) R.color.ok
                        else colorIcon?: R.color.btn_copy_active

                    else R.color.btn_copy_enactive)
            )
        )
    }
}