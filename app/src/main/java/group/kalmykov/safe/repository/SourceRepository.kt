package group.kalmykov.safe.repository;

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import group.kalmykov.safe.dao.SourceDao
import group.kalmykov.safe.entity.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SourceRepository(private val sourceDao: SourceDao) : ViewModel()  {


    val sources = sourceDao.all().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


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
    fun delete(source:Source) {
        viewModelScope.launch {
            sourceDao.delete(source)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            sourceDao.deleteAll()
        }
    }
}