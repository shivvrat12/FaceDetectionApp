package com.pupilmesh.assignment.domain.repository

import androidx.paging.PagingData
import com.pupilmesh.assignment.domain.model.MangaData
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    fun getPaginatedMangas(pageSize: Int): Flow<PagingData<MangaData>>
}
