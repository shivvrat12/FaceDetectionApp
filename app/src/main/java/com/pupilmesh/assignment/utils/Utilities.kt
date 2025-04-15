package com.pupilmesh.assignment.utils

import com.pupilmesh.assignment.data.local.UserEntity
import com.pupilmesh.assignment.data.local.MangaEntity
import com.pupilmesh.assignment.data.remote.dto.MangaDto
import com.pupilmesh.assignment.data.remote.dto.MangaResponseDto
import com.pupilmesh.assignment.domain.model.MangaData
import com.pupilmesh.assignment.domain.model.MangaResponse
import com.pupilmesh.assignment.domain.model.User


fun UserEntity.toDomain(): User {
    return User(email = email, password = password)
}

fun User.toEntity(): UserEntity {
    return UserEntity(email = email, password = password)
}

fun MangaEntity.toDomain(): MangaData = MangaData(
    authors, create_at, genres, id, nsfw, status, sub_title, summary,
    thumb, title, total_chapter, type, update_at
)

fun MangaData.toEntity(): MangaEntity = MangaEntity(
    authors = authors, create_at = create_at, genres = genres, id = id, nsfw = nsfw,
    status = status, sub_title = sub_title, summary = summary, thumb = thumb, title = title,
    total_chapter = total_chapter, type = type, update_at = update_at
)


fun MangaDto.toDomain(): MangaData {
    return MangaData(
        authors = authors,
        create_at = createdAt,
        genres = genres,
        id = id,
        nsfw = nsfw,
        status = status,
        sub_title = subTitle,
        summary = summary,
        thumb = thumb,
        title = title,
        total_chapter = totalChapter,
        type = type,
        update_at = updatedAt
    )
}

fun MangaResponseDto.toDomain(): MangaResponse {
    return MangaResponse(
        code = code,
        data = mangaList.map { it.toDomain() }
    )
}