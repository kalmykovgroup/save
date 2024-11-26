package group.kalmykov.safe.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import group.kalmykov.safe.data.dao.SourceDao
import group.kalmykov.safe.data.entity.Source
import group.kalmykov.safe.ui.components.SortOrder

/*
class SourcePaging(private val sourceDao: SourceDao,
                   private val query: String, // Поисковая строка
                   private val sortOrder: SortOrder // Порядок сортировки
                         ) : PagingSource<Int, Source>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Source> {
        val page = params.key ?: 0 // Первый запрос, если key null
        val pageSize = params.loadSize


        return  try {
            val data = when (sortOrder) {
                SortOrder.ASCENDING -> sourceDao.getFilteredAndSortedByDateAsc(query, page * pageSize, pageSize)
                SortOrder.DESCENDING -> sourceDao.getFilteredAndSortedByDateDesc(query, page * pageSize, pageSize)
            }

           LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1, // Предыдущая страница
                nextKey = if (data.isEmpty()) null else page + 1 // Следующая страница
            )
        } catch (e: Exception) {
           LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Source>): Int? {
        // Optional:  Определяет начальный ключ для обновления данных
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1) ?: 0
        }
    }
}*/
