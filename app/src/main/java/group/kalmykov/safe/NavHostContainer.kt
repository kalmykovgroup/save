package group.kalmykov.safe

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import group.kalmykov.safe.models.Routes
import group.kalmykov.safe.navigation.HomeScreen
import group.kalmykov.safe.navigation.PassScreen

@Composable
fun NavHostContainer(navController: NavHostController,
                     padding: PaddingValues
) {


    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = Routes.Pass.route,

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable(Routes.Home.route) {
                HomeScreen()
            }

            // route : search
            composable(Routes.Pass.route) {
                PassScreen()
            }


        })

}