package com.pupilmesh.assignment.data.local.preferences

import androidx.paging.PagingSource
import com.pupilmesh.assignment.data.local.MangaDao
import com.pupilmesh.assignment.data.local.MangaEntity
import com.pupilmesh.assignment.domain.repository.MangaOfflRepository
import javax.inject.Inject

class MangaOfflRepositoryImpl @Inject constructor(
    private val mangaDao: MangaDao
) : MangaOfflRepository {

    override fun getAllManga(): PagingSource<Int, MangaEntity> = mangaDao.getAllManga()

    override suspend fun insertAll(mangas: List<MangaEntity>) = mangaDao.insertAll(mangas)

    override suspend fun clearAll() = mangaDao.clearAll()
}
