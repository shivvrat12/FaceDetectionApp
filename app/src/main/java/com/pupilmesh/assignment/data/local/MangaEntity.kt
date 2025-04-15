package com.pupilmesh.assignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga_table")
data class MangaEntity(
    @PrimaryKey(autoGenerate = true)
    val ids : Int = 0,
    val authors: List<String>,
    val create_at: Long,
    val genres: List<String>,
    val id: String,
    val nsfw: Boolean,
    val status: String,
    val sub_title: String,
    val summary: String,
    val thumb: String,
    val title: String,
    val total_chapter: Int,
    val type: String,
    val update_at: Long
)
