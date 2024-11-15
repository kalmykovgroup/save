package group.kalmykov.safe.viewModels

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import group.kalmykov.safe.functions.vibrator
import group.kalmykov.safe.models.Routes
import group.kalmykov.safe.ui.screens.LoginScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.concurrent.Executor



class LoginViewModel(private var navController: NavHostController) : ViewModel() {

    var passBoxArray = mutableStateListOf<Int?>(null, null, null, null, null)
    var currentIndex = 0
    private var isPasswordLogin = false
    var isSuccess: Boolean? by mutableStateOf(null)

    @Composable
    fun ShowScreen(){
        val context = LocalContext.current as FragmentActivity
        val biometricManager = BiometricManager.from(context)

        var supportsBiometrics by remember { mutableStateOf(false) }

        supportsBiometrics = when (biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                //Toast.makeText(context, "Биометрия недоступна", Toast.LENGTH_LONG).show()
                false
            }
        }

        LoginScreen(this, supportsBiometrics) {authenticate(context) }

        if(supportsBiometrics && !isPasswordLogin && isSuccess != true){
            authenticate(context)
        }
    }

    val pin  = "11111";

   private fun SingIn(pin : String): Boolean {

       return this.pin == pin;

    }

    fun Delete(){

        if(currentIndex > 0) passBoxArray[--currentIndex] = null
    }

    fun Enter(digit: Int, context : Context){
            if(currentIndex < 0 || currentIndex >= passBoxArray.size) return

            passBoxArray[currentIndex++] = digit

            if(currentIndex == passBoxArray.size){

                val numberStr = passBoxArray.joinToString("")
                if(SingIn(numberStr)){

                    isSuccess = true

                    viewModelScope.launch(Dispatchers.Main) {
                        delay(300)
                        navController.navigate(Routes.Home.route);
                    }


                }else{
                    vibrator(context)

                    isSuccess = false

                    viewModelScope.launch(Dispatchers.Main) {
                        delay(300)
                        isSuccess = null
                        currentIndex = 0
                        passBoxArray.replaceAll{null}
                    }
                }
            }

    }


    private fun authenticate(context: FragmentActivity) {

        val executor = context.mainExecutor
        val biometricPrompt = BiometricPrompt(
            context,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    //Toast.makeText(context, "Аутентифкация пройдена", Toast.LENGTH_LONG).show()
                    isSuccess = true
                    navController.navigate(Routes.Home.route)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                   // Toast.makeText(context, "Ошибка при аутентификации: $errString", Toast.LENGTH_LONG).show()
                }

                override fun onAuthenticationFailed() {
                 //   Toast.makeText(context, "Не удалось пройти аутентификацию", Toast.LENGTH_LONG).show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Биометрическая аутентификация")
            .setDescription("Используйте отпечаток пальца или камеру для аутентификации")
            .setNegativeButtonText("Ввести пароль")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private val passwordFileName = "secure_prefs"
    private val passwordKeyName = "encrypted_password"

    fun saveEncryptedPassword(context: Context, password: String) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            passwordFileName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        sharedPreferences.edit().putString(passwordKeyName, password).apply()
    }

    fun authenticateAndRetrievePassword(context: Context, onPasswordRetrieved: (String?) -> Unit) {
        val executor = ContextCompat.getMainExecutor(context)

        val biometricPrompt = BiometricPrompt(context as FragmentActivity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                // Извлечение пароля из EncryptedSharedPreferences
                val masterKey = MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()

                val sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    passwordFileName,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                val password = sharedPreferences.getString(passwordKeyName, null)
                onPasswordRetrieved(password) // Возвращаем расшифрованный пароль
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Аутентификация")
            .setSubtitle("Введите биометрические данные для доступа к паролю")
            .setDeviceCredentialAllowed(true)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }


}