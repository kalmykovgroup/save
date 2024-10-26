package group.kalmykov.safe.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PassItem(number: Int, action : () -> Unit){
    Button(
        onClick = action,
        shape = CircleShape
    ){
        Text(
            text = "$number",
            Modifier.padding(10.dp),
            color = Color.Black
        )
    }
}