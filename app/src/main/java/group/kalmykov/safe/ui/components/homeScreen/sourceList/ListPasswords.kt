package group.kalmykov.safe.ui.components.homeScreen.sourceList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.models.Password

@Composable
fun ListPasswords(passwords: MutableList<Password>, isOpenSource : MutableState<Boolean>){

    if(isOpenSource.value){

        Column {
            passwords.forEach { password ->
                PasswordUI(password)
            };
        }

    }



}

@Composable
fun PasswordUI(password : Password){

    val isShow = remember { mutableStateOf(false) }

    Row{
        Column {
            Row(modifier = Modifier.height(80.dp)){
                Box(modifier = Modifier.weight(1f)){
                    Column(modifier = Modifier.fillMaxSize()){

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clickable(onClick = {}),
                            contentAlignment = Alignment.CenterStart){
                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically){

                                IconCopy()

                                password.key?.let {
                                    Row(verticalAlignment = Alignment.CenterVertically){
                                        Text(text = "key: ",
                                            style = TextStyle(color = Color.LightGray, fontSize = 14.sp),
                                            modifier = Modifier
                                                .width(50.dp)
                                            , textAlign = TextAlign.Start)
                                        Text(text = it)
                                    }
                                }

                            }
                        }

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .clickable(onClick = {}),
                            contentAlignment = Alignment.CenterStart){

                            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                                Row(modifier = Modifier.fillMaxSize().weight(1f), verticalAlignment = Alignment.CenterVertically){
                                    IconCopy()

                                    Row(verticalAlignment = Alignment.CenterVertically){
                                        Text(text = "value: ",
                                            style = TextStyle(color = Color.LightGray, fontSize = 14.sp),
                                            modifier = Modifier
                                                .width(50.dp)
                                            , textAlign = TextAlign.Start)
                                        Text(text = password.value)
                                    }
                                }

                                BtnShow(isShow)
                            }
                        }
                    }
                }

                BtnDelete()

            }
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }

    }

}


@Composable
fun IconCopy(){
    Box(modifier = Modifier
        .width(30.dp)
        .padding(5.dp)
        , contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.copy),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(colorResource(R.color.icon_copy))
        )
    }
}


@Composable
fun BtnShow(isShow : MutableState<Boolean>){

    var thumbIcon by remember {
        mutableIntStateOf(if (isShow.value) R.drawable.eye_visible else R.drawable.eye_hide)
    }

    Box(modifier = Modifier
        .width(40.dp)
        .height(30.dp)
        //.background(color = colorResource(R.color.show_background))
        .clip(shape = RoundedCornerShape(5.dp))
        .border(width = 1.dp, color = Color.LightGray, shape= RoundedCornerShape(5.dp))
        .clickable(onClick = {
            isShow.value = !isShow.value
            thumbIcon = if (isShow.value) R.drawable.eye_visible else R.drawable.eye_hide
        })
        , contentAlignment = Alignment.Center
    ){
        Image(
            imageVector = ImageVector.vectorResource(thumbIcon),
            contentDescription = "?",
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(29.dp),
            colorFilter = ColorFilter.tint(colorResource(R.color.icon_eye)),
            )
    }
}


@Composable
fun BtnDelete(){
    Box(modifier = Modifier
        .width(30.dp)
        .fillMaxHeight()
        .border(width = 2.dp, color = Color.White)
        .background(color = colorResource(R.color.delete_background))
        .padding(3.dp),
         contentAlignment = Alignment.Center
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
