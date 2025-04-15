package com.pupilmesh.assignment.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import android.util.Log
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.pupilmesh.assignment.data.local.MangaEntity
import com.pupilmesh.assignment.domain.repository.MangaOfflRepository
import com.pupilmesh.assignment.utils.toDomain
import com.pupilmesh.assignment.utils.toEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MangaRemoteMediator @Inject constructor(
    private val remoteDataSource: MangaRemoteDataSource,
    private val offlRepository: MangaOfflRepository
) : RemoteMediator<Int, MangaEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MangaEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) 1 else (lastItem.id.toIntOrNull() ?: 0) / state.config.pageSize + 1
                }
                LoadType.PREPEND -> {
                    Log.d("RemoteMediator", "PREPEND called — skipping")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            Log.d("RemoteMediator", "Loading page $page with size ${state.config.pageSize}")

            val response = remoteDataSource.fetchMangas(page, state.config.pageSize)
            Log.d("RemoteMediator", "API returned ${response.mangaList.size} mangas")

            val entities = response.mangaList.map { it.toDomain().toEntity() }
            Log.d("RemoteMediator", "Mapped ${entities.size} entities")

            if (loadType == LoadType.REFRESH) {
                Log.d("RemoteMediator", "Clearing existing DB entries")
                offlRepository.clearAll()
            }

            offlRepository.insertAll(entities)
            Log.d("RemoteMediator", "Inserted into DB")

            return MediatorResult.Success(endOfPaginationReached = entities.isEmpty())
        } catch (e: Exception) {
            Log.e("RemoteMediator", "Error loading data", e)
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}
