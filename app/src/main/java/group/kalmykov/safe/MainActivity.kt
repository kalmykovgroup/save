package group.kalmykov.safe

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import group.kalmykov.safe.navigation.NavGraph
import group.kalmykov.safe.ui.theme.SafeTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeTheme {
                NavGraph(LocalContext.current)

                /*val navController = rememberNavController()
                MainViewModel(navController = navController, LocalContext.current).NavHostContainer()*/
            }
        }
    }


}


