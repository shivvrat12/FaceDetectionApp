package com.pupilmesh.assignment.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MangaDto(
    val authors: List<String>,
    @SerializedName("create_at")
    val createdAt: Long,
    val genres: List<String>,
    val id: String,
    val nsfw: Boolean,
    val status: String,
    @SerializedName("sub_title")
    val subTitle: String,
    val summary: String,
    val thumb: String,
    val title: String,
    @SerializedName("total_chapter")
    val totalChapter: Int,
    val type: String,
    @SerializedName("update_at")
    val updatedAt: Long
)
