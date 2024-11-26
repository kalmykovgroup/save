package group.kalmykov.safe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R

@Composable
fun BtnPassVisible(passwordVisibility : Boolean, setPasswordVisibility : (Boolean) -> Unit){

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