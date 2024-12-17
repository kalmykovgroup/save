package group.kalmykov.safe.viewModels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import group.kalmykov.safe.navigation.Destinations
import group.kalmykov.safe.ui.components.vibrator
import group.kalmykov.safe.navigation.NavGraphViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Console
import javax.crypto.AEADBadTagException

class LoginViewModel(private val context: Context, private val navGraphViewModel: NavGraphViewModel): ViewModel() {

    private val isFirstLaunchFlagKey = "isFirstLaunch"

    private val passwordFileName = "secure_prefs"
    private val passwordKeyName = "encrypted_password"

    var isMethodPasswordLogin = false
    var isSuccess: Boolean? by mutableStateOf(null)

    var supportsBiometrics by  mutableStateOf(false)

    private val sharedPreferences = createEncryptedSharedPreferences(context)

    init{
        val biometricManager = BiometricManager.from(context)

        supportsBiometrics = when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> { //"Биометрия недоступна"
                false
            }
        }
    }

    private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
        val passwordFileName = "encrypted_prefs"

        try {
            Log.d("EncryptedPrefs", "Попытка создания MasterKey и EncryptedSharedPreferences")

            // Создаем MasterKey
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            Log.d("EncryptedPrefs", "MasterKey успешно создан")

            // Создаем EncryptedSharedPreferences
            return EncryptedSharedPreferences.create(
                context,
                passwordFileName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: AEADBadTagException) {
            Log.e("EncryptedPrefs", "Ошибка AEADBadTagException: ${e.message}, удаляю файл")

            // Удаляем поврежденный файл
            context.deleteFile(passwordFileName)

            // Повторная попытка создания
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            Log.d("EncryptedPrefs", "MasterKey пересоздан")

            return EncryptedSharedPreferences.create(
                context,
                passwordFileName,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            Log.e("EncryptedPrefs", "Неизвестная ошибка: ${e.message}", e)
            throw RuntimeException("Ошибка при создании EncryptedSharedPreferences", e)
        }
    }


    fun isFirstLogin(): Boolean {//Проверяем что это не первый вход
        return sharedPreferences.getBoolean(isFirstLaunchFlagKey, true)
    }


    //Метод обработки обычного успешного входа в систему
    private fun successfulLogin(){
        navGraphViewModel.navController.navigate(Destinations.Home.route){
            popUpTo(Destinations.Login.route) { inclusive = true }
        }
    }


    //метод вызывается при успешном первом входе (успешное создание пароля для входа)
    fun successfulFirstLogin(){
        sharedPreferences.edit().putBoolean(isFirstLaunchFlagKey, false).apply()
    }

    //Метод для сохранения пароля
    fun saveEncryptedPassword(password: String) {
        sharedPreferences.edit().putString(passwordKeyName, password).apply()
    }

    //Вызываем метод когда нам нужно вызвать диалогое окно для входа по отпечатку
    fun authenticate() {
        val context: FragmentActivity = context as FragmentActivity

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
                    isMethodPasswordLogin = true
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

    fun singIn(pin : String, context: Context): Boolean {

        val pass = sharedPreferences.getString(passwordKeyName, null)

        if(pass == null){
           // mainViewModel.restartApp()

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
            successfulLogin()
        }
        return true
    }
}
