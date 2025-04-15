package com.pupilmesh.assignment.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangas: List<MangaEntity>)

    @Query("SELECT * FROM manga_table")
    fun getAllManga(): PagingSource<Int, MangaEntity>

    @Query("DELETE FROM manga_table")
    suspend fun clearAll()
}