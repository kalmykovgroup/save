package group.kalmykov.safe.viewModels

import android.content.Context
import android.content.SharedPreferences
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import group.kalmykov.safe.functions.vibrator
import group.kalmykov.safe.ui.screens.LoginScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import androidx.fragment.app.FragmentActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class LoginViewModel(private val callbackOnSuccessfulLogin: () -> Unit, private val mainViewModel: MainViewModel, context: Context) : ViewModel() {

    private val passwordFileName = "secure_prefs"
    private val passwordKeyName = "encrypted_password"

    var currentIndex = 0
    private var isPasswordLogin = false
    var isSuccess: Boolean? by mutableStateOf(null)

    var supportsBiometrics by  mutableStateOf(false)

    init{
        val biometricManager = BiometricManager.from(context)

        supportsBiometrics = when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> { //"Биометрия недоступна"
                false
            }
        }
    }


    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        passwordFileName,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Composable
    fun ShowScreen(){
        val context = LocalContext.current as FragmentActivity

        LoginScreen(this) {authenticate(context) }

        if(supportsBiometrics && !isPasswordLogin && isSuccess != true){
            authenticate(context)
        }
    }


    fun singIn(pin : String, context: Context): Boolean {

        val pass = sharedPreferences.getString(passwordKeyName, null)

        if(pass == null){
            mainViewModel.restartApp()

            return false
        }

       if(pin != pass){
           vibrator(context)

           viewModelScope.launch(Dispatchers.Main) {
               isSuccess = false
               delay(300)
               isSuccess = null
           }
           return false
       }

       isSuccess = true

       viewModelScope.launch(Dispatchers.Main) {
           delay(300)
           callbackOnSuccessfulLogin()
       }
       return true
    }



    private fun authenticate(context: FragmentActivity) {

        val executor = context.mainExecutor
        val biometricPrompt = BiometricPrompt(
            context,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded( //"Аутентификация пройдена"
                    result: BiometricPrompt.AuthenticationResult) {

                    if(!singIn(sharedPreferences.getString(passwordKeyName, null) ?: "", context)){
                        Toast.makeText(context, "Не удалось пройти аутентификацию", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {//Ошибка при аутентификации
                    isPasswordLogin = true
                }

                override fun onAuthenticationFailed() {//Не удалось пройти аутентификацию
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


    fun saveEncryptedPassword(password: String) {
        sharedPreferences.edit().putString(passwordKeyName, password).apply()
    }



}