package group.kalmykov.safe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import group.kalmykov.safe.ui.theme.SafeTheme
import group.kalmykov.safe.viewModels.MainViewModel

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeTheme {
                val navController = rememberNavController()
                MainViewModel(navController = navController, LocalContext.current).NavHostContainer()
            }
        }
    }
}


