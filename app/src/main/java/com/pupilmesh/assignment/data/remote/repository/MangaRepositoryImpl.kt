package com.pupilmesh.assignment.data.remote.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.pupilmesh.assignment.data.remote.MangaRemoteDataSource
import com.pupilmesh.assignment.data.remote.MangaRemoteMediator
import com.pupilmesh.assignment.data.remote.paging.MangaPagingSource
import com.pupilmesh.assignment.domain.model.MangaData
import com.pupilmesh.assignment.domain.repository.MangaOfflRepository
import com.pupilmesh.assignment.domain.repository.MangaRepository
import com.pupilmesh.assignment.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val remoteMediator: MangaRemoteMediator,
    private val offlRepository: MangaOfflRepository
) : MangaRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPaginatedMangas(pageSize: Int): Flow<PagingData<MangaData>> {
        return Pager(
            config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                Log.d("PagingSourceFactory", "Loading from DB")
                offlRepository.getAllManga() }
        ).flow
            .map { pagingData ->
                Log.d("PagerFlow", "Received PagingData")
                pagingData.map {
                    Log.d("PagerFlow", "Mapping entity: $it")
                    it.toDomain()
                }
            }
    }

}

