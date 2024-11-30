package group.kalmykov.safe.data.repository;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.Query
import group.kalmykov.safe.data.dao.SourceDao
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SourceRepository(private val sourceDao: SourceDao) : ViewModel()  {

/*
   val sources: StateFlow<List<Source>> = sourceDao.all()
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed( 0), emptyList())*/

    fun all(query: String): Flow<List<Source>> {
        return sourceDao.all("%$query%")
          //  .distinctUntilChanged()
            //.stateIn(viewModelScope, SharingStarted.WhileSubscribed( 0), emptyList())
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