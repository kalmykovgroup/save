package group.kalmykov.safe.viewModels

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import group.kalmykov.safe.models.Source
import group.kalmykov.safe.ui.screens.HomeScreen
import kotlinx.coroutines.launch


class HomeViewModel(private val mainViewModel : MainViewModel) : ViewModel() {

    val sources : MutableList<Source> = mutableStateListOf()


    private val homeScreen : HomeScreen = HomeScreen(this)
    //val sourceContract = SourceContract(mainViewModel.dbHelper)


    @SuppressLint("Recycle")
    @Composable
    fun ShowScreen(){


        val coroutine = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit){
            coroutine.launch {
                mainViewModel.dbContext.sources.items().collect{ item ->
                    sources.add(item)
                }
            }
        }




     homeScreen.Show()
    }



}

