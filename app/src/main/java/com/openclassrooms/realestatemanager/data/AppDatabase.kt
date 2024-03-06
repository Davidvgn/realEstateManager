package com.openclassrooms.realestatemanager.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.data.agent.AgentDao
import com.openclassrooms.realestatemanager.data.pictures.PicturesDao
import com.openclassrooms.realestatemanager.data.real_estates.RealEstateDao
import com.openclassrooms.realestatemanager.data.utils.Converters
import com.openclassrooms.realestatemanager.domain.agent.AgentEntity
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import java.util.concurrent.Executor

@Database(entities = [RealEstateEntity::class, PicturesEntity::class, AgentEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRealEstateDao(): RealEstateDao
    abstract fun getPicturesDao(): PicturesDao
    abstract fun getAgentDao(): AgentDao

    companion object {
        private const val DATABASE_NAME = "RealEstate_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(
            application: Application,
            ioExecutor: Executor
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(application, ioExecutor).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(
            application: Application,
            ioExecutor: Executor
        ): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioExecutor.execute {
                            val agentDao = getInstance(application, ioExecutor).getAgentDao()
                            agentDao.insert(AgentEntity(1L, "Ted Mosby"))
                            agentDao.insert(AgentEntity(2L, "Robin Scherbatsky"))
                            agentDao.insert(AgentEntity(3L, "Marshall Eriksen"))
                            agentDao.insert(AgentEntity(4L, "Lily Aldrin"))
                            agentDao.insert(AgentEntity(5L, "Barney Stinson"))
                            agentDao.insert(AgentEntity(6L, "Tracy McConnell"))
                        }
                    }
                })
                .build()
        }
    }
}
