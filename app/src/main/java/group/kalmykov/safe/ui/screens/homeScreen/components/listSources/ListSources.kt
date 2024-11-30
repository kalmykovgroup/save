/*
package group.kalmykov.safe.ui.components.homeScreen.listSources
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import group.kalmykov.safe.entity.Source
import group.kalmykov.safe.ui.screens.homeScreen.HomeScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier


class ListSources(val homeScreen: HomeScreen) : ViewModel() {

  //  private var activeSource : Source? by mutableStateOf(null)

    val listIdSelectedSources = ArrayList<Int>()

    var isSelectedSources: Boolean by mutableStateOf(false)

    var openSource: Source? by mutableStateOf(null)

    @Composable
    fun Show(){
        val sources by homeScreen.sourceRepository.getSourcesAndSortedEntities(homeScreen.searchComponent.search).collectAsState()

        LazyColumn(){
          itemsIndexed(sources, key = { _, source -> source.id }){ index, source ->
            SourceItem(source = source, zIndex = sources.size - index.toFloat(), parent = this@ListSources)
          }
        }

    }

}
*/
