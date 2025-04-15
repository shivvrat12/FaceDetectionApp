package com.pupilmesh.assignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class UserEntity (
    @PrimaryKey val email: String,
    val password: String,
)