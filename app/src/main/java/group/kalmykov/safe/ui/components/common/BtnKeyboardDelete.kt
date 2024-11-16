package group.kalmykov.safe.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R

@Composable
fun BtnKeyboardDelete(action: () -> Unit) {
    Button(
        onClick = {action() },
        modifier = Modifier
            .fillMaxSize(),
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

