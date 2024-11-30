package group.kalmykov.safe.ui.screens.homeScreen.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.BtnCopy
import group.kalmykov.safe.ui.screens.homeScreen.components.dialog.generatePasswordDialog.GeneratePasswordDialog

@Composable
fun SourceFormDialog(cancel: () -> Unit, save: (Source) -> Unit, source : Source? = null,  topBlock: (@Composable () -> Unit)? = null){

    var host by remember { mutableStateOf(source?.host ?: "") }
    var username by remember { mutableStateOf(source?.username ?: "") }
    var password by remember { mutableStateOf(source?.password ?: "") }
    var description by remember { mutableStateOf(source?.description ?: "") }

   var isOpenDialogGeneratePassword by remember { mutableStateOf(false) }

    if(isOpenDialogGeneratePassword){
        GeneratePasswordDialog({password = it}, { isOpenDialogGeneratePassword = false })
    }

    MyCustomDialog(
        onDismissRequest = {cancel()},
        content = {
            Column {

                val modifier = Modifier.height(70.dp)

                topBlock?.let { it() }

                MyOutlinedTextField(value = host, onValueChange = {host = it}, label = "Host",
                    modifier = modifier, multicolored = true){
                    BtnCopy(value = host)
                }
                MyOutlinedTextField(value = username, onValueChange = {username = it}, label = "Username",
                    modifier = modifier, multicolored = true){
                    BtnCopy(value = username)
                }
                MyOutlinedTextField(value = password, onValueChange = {password = it}, label = "Password",
                    modifier = modifier, multicolored = true){

                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable {
                            isOpenDialogGeneratePassword = true
                        }, contentAlignment = Alignment.Center){
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.password_generate),
                            contentDescription = "pass",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.height(29.dp),
                            colorFilter = ColorFilter.tint(colorResource(R.color.password_generate)),
                        )
                    }

                    BtnCopy(value = password)
                }

                MyOutlinedTextField(value = description,
                    onValueChange = {description = it},
                    label = "Description",
                    modifier = Modifier.height(130.dp)
                ){
                    BtnCopy(value = description)
                }

                Row (modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.End){
                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick = {cancel()}
                    ) {
                        Text("Отменить", fontSize = 17.sp)
                    }

                    Button(
                        modifier = Modifier.padding(5.dp, 0.dp), shape = RoundedCornerShape(5.dp),
                        onClick = {

                            if(source != null){
                                source.host = host
                                source.username = username
                                source.password = password
                                source.description = description
                            }

                            val newSource : Source =
                                source
                                    ?: Source(
                                        host = host,
                                        username = username,
                                        password = password,
                                        description = description
                                    )


                            save(newSource); cancel();}
                    ) {
                        Text("Сохранить", fontSize = 17.sp)
                    }
                }
            }
        }
    )

}





