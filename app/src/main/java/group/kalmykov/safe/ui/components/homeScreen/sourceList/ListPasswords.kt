package group.kalmykov.safe.ui.components.homeScreen.sourceList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.models.Password

@Composable
fun PasswordsList(passwords: MutableList<Password>){

    val isShow = remember { mutableStateOf(false) }

    BtnCopy()
    BtnShow(isShow)
    BtnDelete()
}


@Composable
fun BtnCopy(){
    Box(modifier = Modifier
        .width(50.dp)
        .height(30.dp)
        .background(color = colorResource(R.color.eye_background))
        , contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.copy),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(23.dp),
            colorFilter = ColorFilter.tint(colorResource(R.color.black))
        )
    }
}


@Composable
fun BtnShow(isShow : MutableState<Boolean>){

    val thumbIcon by remember {
        mutableIntStateOf(if (isShow.value) R.drawable.eye_visible else R.drawable.eye_hide)
    }

    Box(modifier = Modifier
        .width(50.dp)
        .height(30.dp)
        .background(color = colorResource(R.color.show_background))
        , contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(thumbIcon),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(29.dp).clickable(onClick = {isShow.value = !isShow.value}),
            colorFilter = ColorFilter.tint(colorResource(R.color.black)),

            )
    }
}


@Composable
fun BtnDelete(){
    Box(modifier = Modifier
        .width(50.dp)
        .height(30.dp)
        .background(color = colorResource(R.color.delete_background))
        , contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.delete_icon),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(25.dp),
            colorFilter = ColorFilter.tint(colorResource(R.color.black))
        )
    }
}
