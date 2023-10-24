package com.openclassrooms.realestatemanager.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val surname: String,
    val name: String,
    val email: String)