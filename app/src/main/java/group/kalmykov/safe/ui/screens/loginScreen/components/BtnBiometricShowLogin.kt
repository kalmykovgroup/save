package group.kalmykov.safe.ui.screens.loginScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R
import group.kalmykov.safe.viewModels.LoginViewModel

@Composable
fun BtnBiometricShowLogin(loginViewModel: LoginViewModel){
    //Отпечаток пальца
    Button(
        onClick = { loginViewModel.authenticate()  },
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent),
        contentPadding = PaddingValues()

    ){
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.fingerprint),
            contentDescription = "Отпечаток",
            modifier = Modifier.size(50.dp, 50.dp).align(Alignment.CenterVertically),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}