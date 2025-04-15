package com.pupilmesh.assignment.domain.repository

import com.pupilmesh.assignment.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
    suspend fun insertUser(user: User)
}