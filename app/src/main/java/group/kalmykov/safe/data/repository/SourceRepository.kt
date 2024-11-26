package group.kalmykov.safe.data.repository;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import group.kalmykov.safe.data.dao.SourceDao
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SourceRepository(private val sourceDao: SourceDao) : ViewModel()  {

/*
   val sources: StateFlow<List<Source>> = sourceDao.all()
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed( 0), emptyList())*/

    fun getSources(query: String, sortOrder: SortOrder): Flow<PagingData<Source>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false, // Обычно лучше отключить для производительности
                prefetchDistance = 50, // Сколько страниц предзагружать
                initialLoadSize = 20 // Размер первой страницы
            ),
            pagingSourceFactory = { sourceDao.getSources(query, sortOrder.name) }
        ).flow
    }

    fun getItems(query: String): PagingSource<Int, Source> {
        return sourceDao.getItems("%$query%")
    }


    fun add(source: Source) {
        viewModelScope.launch {
            sourceDao.insert(source)
        }
    }

    fun update(source: Source) {
        viewModelScope.launch {
            sourceDao.update(source)
        }
    }

    fun remove(source: Source) {
        viewModelScope.launch {
            sourceDao.delete(source)
        }
    }
    fun remove(sources: List<Source>) {
        viewModelScope.launch {
            sourceDao.delete(sources)
        }
    }


}