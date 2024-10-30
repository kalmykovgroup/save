package group.kalmykov.safe.ui.components.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.viewModels.LoginViewModel


@Composable
fun ButtonInputButtons(loginViewModel : LoginViewModel){
    Row(modifier = Modifier
        .padding(10.dp, 0.dp)
        .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.spacedBy(2.dp) ){

        //Кнопка Не могу войти
        Box(modifier = Modifier
            .height(80.dp)
            .weight(1f), contentAlignment = Alignment.Center){
            Button(
                onClick = { },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth() ,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ){
                Text(
                    modifier = Modifier
                        .width(70.dp)
                        .fillMaxWidth(),
                    text = "Не могу войти",
                    fontSize = 17.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                )
            }

        }
        //Кнопка ноль
        Box(modifier = Modifier
            .height(80.dp)
            .weight(1f), contentAlignment = Alignment.Center){
            Button(
                onClick = {loginViewModel.Enter(0) },
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors( containerColor = colorResource(R.color.passButton))
            ){
                Text(
                    text = "0",
                    fontSize = 22.sp,
                    color = Color.Black
                )
            }
        }

        Box(modifier = Modifier
            .height(80.dp)
            .weight(1f), contentAlignment = Alignment.Center){
            if(loginViewModel.currentIndex == 0 ){
                //Отпечаток пальца
                Button(
                    onClick = {  },
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent)

                ){
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.fingerprint),
                        contentDescription = "Отпечаток",
                        modifier = Modifier.size(50.dp, 50.dp)

                    )
                }
            }else{
                //Кнопка удалить
                Button(
                    onClick = {loginViewModel.Delete() },
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent)

                ){
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.delete),
                        contentDescription = "Отпечаток",
                        modifier = Modifier.size(50.dp, 50.dp)

                    )
                }
            }
        }
    }
}