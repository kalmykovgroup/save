package group.kalmykov.safe.ui.screens.homeScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import group.kalmykov.safe.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarContainer(){
    androidx.compose.material3.TopAppBar(
        modifier = Modifier.fillMaxWidth().height(30.dp),
        colors = topAppBarColors(
            containerColor = colorResource(R.color.background_home_screen),
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {

        }
    )
}