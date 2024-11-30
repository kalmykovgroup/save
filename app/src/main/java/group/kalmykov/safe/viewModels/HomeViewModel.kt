package group.kalmykov.safe.viewModels

import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.navigation.NavGraphViewModel
import group.kalmykov.safe.data.repository.SourceRepository
import group.kalmykov.safe.ui.components.SortOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch





@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(context: Context, navGraphViewModel: NavGraphViewModel): ViewModel() {
    private val _query = MutableStateFlow("")
    private val _sortOrder = MutableStateFlow(SortOrder.DESCENDING)

    val query: StateFlow<String> = _query
    val sortOrder: StateFlow<SortOrder> = _sortOrder

    private val sourceDao = navGraphViewModel.db.sourceDao()
    private val sourceRepository: SourceRepository = SourceRepository(sourceDao)

    private val _items = MutableStateFlow<List<Source>>(emptyList())

    val sources: StateFlow<List<Source>> = _items

    // Состояние для управления прокруткой
    // StateFlow для хранения индекса прокрутки
    private val _scrollToIndex = MutableStateFlow<Int?>(null)
    val scrollToIndex: StateFlow<Int?> = _scrollToIndex.asStateFlow()

    // Метод для установки индекса прокрутки
    fun scrollToIndex(index: Int) {
        _scrollToIndex.value = index
    }

    // Сбрасываем индекс после прокрутки
    fun onScrollCompleted() {
        _scrollToIndex.value = null
    }

    init {
        viewModelScope.launch {
            _query.flatMapLatest { query ->
                sourceRepository.all(query)
            }.collect { itemList ->
                _items.value = itemList
            }
        }
    }


  /*  @OptIn(ExperimentalCoroutinesApi::class)
    val sources: Flow<PagingData<Source>> = combine(query, sortOrder) { query, sortOrder ->
        sourceRepository.getSources(query, sortOrder)
    }.flatMapLatest { it }
        .cachedIn(viewModelScope)*/

   /* @OptIn(ExperimentalCoroutinesApi::class)
    val items = _query.flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 20)) {
            sourceRepository.all(query)
        }.flow.cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .cachedIn(viewModelScope)
*/
    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun updateSortOrder(newSortOrder: SortOrder) {
        _sortOrder.value = newSortOrder
    }

    val listSelectedSources = mutableStateListOf<Source>()
    val isSelectedMode: Boolean by derivedStateOf { listSelectedSources.size > 0}

    fun addSource(source: Source) {
        viewModelScope.launch {
            sourceRepository.add(source)
        }
    }

    fun updateSource(source: Source){
        sourceRepository.update(source)
    }

    fun removeSource(source: Source){
        viewModelScope.launch {
            sourceRepository.remove(source)
        }

    }

    fun deleteSelected(){
        viewModelScope.launch {
            sourceRepository.remove(listSelectedSources.toList())
            listSelectedSources.clear()
        }
    }

    fun closeOpenSources(){
        closePrevOpenSource?.let { it() }
        closePrevOpenSource = null
    }

    var closePrevOpenSource: (() -> Unit)? = null


}


