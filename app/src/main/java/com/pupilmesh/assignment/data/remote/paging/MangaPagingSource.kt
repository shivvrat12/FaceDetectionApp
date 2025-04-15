package com.pupilmesh.assignment.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pupilmesh.assignment.data.remote.MangaRemoteDataSource
import com.pupilmesh.assignment.domain.model.MangaData
import com.pupilmesh.assignment.utils.toDomain
import javax.inject.Inject

class MangaPagingSource @Inject constructor(
    private val remoteDataSource: MangaRemoteDataSource
) : PagingSource<Int, MangaData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaData> {
        val page = params.key ?: 1
        return try {
            val response = remoteDataSource.fetchMangas(page, params.loadSize)
            val mangas = response.mangaList.map { it.toDomain() }
            LoadResult.Page(
                data = mangas,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (response.mangaList.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            Log.e("MangaPagingSource", "Load failed: ${e.localizedMessage}", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MangaData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
