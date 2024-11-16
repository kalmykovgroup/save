package group.kalmykov.safe.repository;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import group.kalmykov.safe.dao.SourceDao
import group.kalmykov.safe.entity.Source
import group.kalmykov.safe.ui.components.homeScreen.search.SearchComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SourceRepository(private val sourceDao: SourceDao) : ViewModel()  {

    //val sources = sourceDao.all().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getSourcesAndSortedEntities(filter: String): StateFlow<List<Source>> {
       return sourceDao.getSourcesAndSortedEntities(filter).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
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

    fun delete(id:Int) {
        viewModelScope.launch {
            sourceDao.delete(id)
        }
    }

    fun deleteAll(ids: List<Int>) {
        viewModelScope.launch {
            sourceDao.deleteItemsByIds(ids)
        }
    }

}