package group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import group.kalmykov.safe.R
import group.kalmykov.safe.functions.vibrator
import group.kalmykov.safe.ui.components.homeScreen.buffer.rememberClipboardText
import group.kalmykov.safe.ui.components.homeScreen.listSources.MyCustomDialog
import group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.BottomButtonsCancelOk
import group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.CustomSlider
import group.kalmykov.safe.ui.components.homeScreen.listSources.generatePasswordDialog.components.SemiCircularScale


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun GeneratePasswordDialog(save: (String) -> Unit, close: () -> Unit){

    var password by remember { mutableStateOf("") }

    val maxLength = 65f
    val minLength = 4f

    var length by remember { mutableIntStateOf(minLength.toInt()) }


    var isUpperCaseLetters by remember { mutableStateOf(true) }
    var isNumbers by remember { mutableStateOf(true) }
    var isSymbols by remember { mutableStateOf(true) }


    MyCustomDialog(
        onDismissRequest = {close()},
        content = {
            Column {
               Text(text = "Уровень защиты", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

               SemiCircularScale(
                   password = password,
                   modifier = Modifier.height(150.dp),
                   backgroundColor = colorResource(R.color.canvasBackgroundGeneratePassword),
                   strokeWidth = 150f
               )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp), verticalAlignment = Alignment.CenterVertically){

                    FlowRow(modifier = Modifier
                        .weight(1f)
                        .padding(start = 40.dp), horizontalArrangement = Arrangement.Center) {
                        repeat(password.length) { index ->
                            var color = Color.White

                            if(password[index].isDigit()){
                                color = colorResource(R.color.canvas_color_level_2)
                            }else if(!password[index].isLetter()){
                                color = colorResource(R.color.canvas_color_level_3)
                            }else if(password[index].isUpperCase()){
                                color = colorResource(R.color.canvas_color_level_1)
                            }

                            Text(text = password[index].toString(), color = color, fontSize = 16.sp)
                        }
                    }

                    BoxBtnCopy(password)
                }

                Text(text = "$length символов", color = Color.White)

                CustomSlider(setCountChar = { length = it }, min = minLength, max = maxLength)

                RowUpperCaseLetters(isUpperCaseLetters, { isUpperCaseLetters = it })
                RowNumbers(isNumbers, {isNumbers = it})
                RowSymbols(isSymbols, {isSymbols = it})

                BoxGenerateButton{
                    password = generatePassword(
                        length = length,
                        includeUpperCase = isUpperCaseLetters,
                        includeNumbers = isNumbers,
                        includeSymbols = isSymbols
                    )
                }

                BottomButtonsCancelOk(cancel = {
                    close()
                }, ok = {
                    save(password)
                    close()
                })
            }
        }
    )
}

@Composable
fun BoxBtnCopy(password: String){
    val context = LocalContext.current
    val clipBoardText by rememberClipboardText()

    Box(modifier = Modifier
        .width(40.dp)
        .height(80.dp) , contentAlignment = Alignment.Center){

        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(
                onClick = {
                    val clipboardManager =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboardManager.setPrimaryClip(
                        ClipData.newPlainText(
                            "text",
                            password
                        )
                    )
                    vibrator(context, milliseconds = 100)
                }, enabled = password.isNotEmpty() && clipBoardText.toString() != password
            )
            , contentAlignment = Alignment.Center
        ){
            Image(
                imageVector = ImageVector.vectorResource(if(clipBoardText.toString() != password) R.drawable.copy else R.drawable.ok),
                contentDescription = "copy",
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(23.dp),
                colorFilter = ColorFilter.tint(colorResource(
                    if(password.isNotEmpty())
                        if(clipBoardText.toString() == password) R.color.ok
                        else R.color.btn_copy_active

                    else R.color.btn_copy_enactive)
                )
            )
        }
    }
}

@Composable
fun RowUpperCaseLetters(isUpperCaseLetters: Boolean, setIsUpperCaseLetters: (Boolean) -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 15.dp)){
        Checkbox(
            checked = isUpperCaseLetters,
            onCheckedChange = setIsUpperCaseLetters
        )

        Box(modifier = Modifier.weight(1f)){
            Text(text = "Заглавные буквы" , color = Color.White)
        }

        Text(text = "ABC", color = colorResource(id = R.color.canvas_color_level_1))
    }
}
@Composable
fun RowNumbers(isNumbers: Boolean, setIsNumbers: (Boolean) -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 15.dp)){
        Checkbox(
            checked = isNumbers,
            onCheckedChange = setIsNumbers
        )

        Box(modifier = Modifier.weight(1f)){
            Text(text = "Цифры" , color = Color.White)
        }

        Text(text = "123", color = colorResource(id = R.color.canvas_color_level_2))
    }
}

@Composable
fun RowSymbols(isSymbols: Boolean, setIsSymbols: (Boolean) -> Unit){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 15.dp)){
        Checkbox(
            checked = isSymbols,
            onCheckedChange = setIsSymbols
        )

        Box(modifier = Modifier.weight(1f)){
            Text(text = "Символы", color = Color.White)
        }

        Text(text = "!&*", color = colorResource(id = R.color.canvas_color_level_3))
    }
}

@Composable
fun BoxGenerateButton(click: () -> Unit){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .background(colorResource(R.color.generate_btn_background))
        .border(
            width = 1.dp,
            color = colorResource(R.color.generate_btn_border),
            shape = RoundedCornerShape(5.dp)
        )
        .clickable {
            click()
        }
        , contentAlignment = Alignment.Center){
        Row(verticalAlignment = Alignment.CenterVertically){
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.generate),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(23.dp),
                colorFilter = ColorFilter.tint(colorResource(R.color.generate_btn_icon))
            )
            Spacer(Modifier.width(10.dp))
            Text(text = "Сгенерировать новый", style = TextStyle(color = colorResource(R.color.generate_btn_text)))
        }
    }
}


fun generatePassword(
    length: Int,
    includeUpperCase: Boolean = true,
    includeNumbers: Boolean = true,
    includeSymbols: Boolean = true
): String {
    require(length > 0) { "Password length must be greater than zero" }

    val lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz"
    val upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val numbers = "0123456789"
    val symbols = "!@#$%^&*()-_+=<>?/"

    val charPool = buildString {
        append(lowerCaseLetters)
        if (includeUpperCase) append(upperCaseLetters)
        if (includeNumbers) append(numbers)
        if (includeSymbols) append(symbols)
    }

    require(charPool.isNotEmpty()) { "Character pool cannot be empty" }

    return (1..length)
        .map { charPool.random() }
        .joinToString("")
}






