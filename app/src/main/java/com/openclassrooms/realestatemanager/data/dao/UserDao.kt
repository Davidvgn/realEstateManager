package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.openclassrooms.realestatemanager.domain.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(userEntity: UserEntity)
}