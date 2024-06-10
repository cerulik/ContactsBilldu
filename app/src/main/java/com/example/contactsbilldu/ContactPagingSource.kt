package com.example.contactsbilldu

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.contactsbilldu.data.source.local.ContactLocalSource
import com.example.contactsbilldu.data.source.local.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactPagingSource(
    private val contactLocalSource: ContactLocalSource,
    private val query: String
) : PagingSource<Int, Contact>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        val page = params.key ?: 0

        return try {
            withContext(Dispatchers.IO) {
                val contacts = if (query.isEmpty()) {
                    contactLocalSource.getContacts(page * params.loadSize, params.loadSize)
                } else {
                    contactLocalSource.searchContacts(
                        query,
                        page * params.loadSize,
                        params.loadSize
                    )
                }

                LoadResult.Page(
                    data = contacts,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (contacts.isEmpty()) null else page + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
