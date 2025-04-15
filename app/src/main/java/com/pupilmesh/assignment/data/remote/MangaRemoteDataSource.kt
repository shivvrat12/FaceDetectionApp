package com.pupilmesh.assignment.data.remote
import com.pupilmesh.assignment.data.remote.api.MangaApiService
import com.pupilmesh.assignment.data.remote.dto.MangaResponseDto
import javax.inject.Inject

class MangaRemoteDataSource @Inject constructor(private val apiService: MangaApiService) {
    suspend fun fetchMangas(page: Int, limit: Int): MangaResponseDto {
        return apiService.getMangas(page = page, limit = limit)
    }
}