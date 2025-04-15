package com.pupilmesh.assignment.data.local

import com.pupilmesh.assignment.domain.model.User
import com.pupilmesh.assignment.domain.repository.UserRepository
import com.pupilmesh.assignment.utils.toDomain
import com.pupilmesh.assignment.utils.toEntity
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toDomain()
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)?.toDomain()
    }

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }
}