package com.openclassrooms.realestatemanager.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.WorkManager
import com.openclassrooms.realestatemanager.data.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.dao.UserDao
import com.openclassrooms.realestatemanager.domain.RealEstateEntity
import com.openclassrooms.realestatemanager.domain.UserEntity

@Database(entities = [UserEntity::class, RealEstateEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getRealEstateDao(): RealEstateDao

    companion object{
        private const val DATABASE_NAME = "RealEstate_database"

        fun create(
            application: Application
            ) : AppDatabase{
            val builder = Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            return builder.build()
        }
    }
}