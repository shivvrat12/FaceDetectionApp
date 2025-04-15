package com.pupilmesh.assignment.domain.model

data class MangaResponse(
    val code: Int,
    val `data`: List<MangaData>
)