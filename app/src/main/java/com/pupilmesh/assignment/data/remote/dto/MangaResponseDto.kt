package com.pupilmesh.assignment.data.remote.dto
import com.google.gson.annotations.SerializedName

data class MangaResponseDto(
    val code: Int,
    @SerializedName("data")
    val mangaList: List<MangaDto>
)
