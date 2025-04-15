package com.pupilmesh.assignment.data.remote.api

import com.pupilmesh.assignment.data.remote.dto.MangaResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface MangaApiService {
    @GET("/manga/fetch")
    suspend fun getMangas(
        @Header("X-RapidAPI-Key") apiKey: String = "36762f6c00msh8d5d8b917933942p19c942jsnbdb30194b0db",
        @Header("X-RapidAPI-Host") apiHost: String = "mangaverse-api.p.rapidapi.com",
        @Query("page") page: Int,
        @Query("genres") genres: String = "Harem,Fantasy",
        @Query("nsfw") nsfw: Boolean = true,
        @Query("type") type: String = "all",
        @Query("limit") limit: Int
    ): MangaResponseDto
}