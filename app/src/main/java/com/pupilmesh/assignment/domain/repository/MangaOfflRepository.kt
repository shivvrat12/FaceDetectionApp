package com.pupilmesh.assignment.domain.repository

import androidx.paging.PagingSource
import com.pupilmesh.assignment.data.local.MangaEntity

interface MangaOfflRepository {
    fun getAllManga(): PagingSource<Int, MangaEntity>
    suspend fun insertAll(mangas: List<MangaEntity>)
    suspend fun clearAll()
}

