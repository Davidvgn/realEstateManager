package com.openclassrooms.realestatemanager.data

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.openclassrooms.realestatemanager.data.agent.AgentDao
import com.openclassrooms.realestatemanager.data.pictures.PicturesDao
import com.openclassrooms.realestatemanager.data.real_estates.RealEstateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        application: Application,
        ioExecutor: Executor
    ): AppDatabase = AppDatabase.getInstance(application, ioExecutor)

    @Singleton
    @Provides
    fun provideRealEstateDao(appDatabase: AppDatabase): RealEstateDao =
        appDatabase.getRealEstateDao()

    @Singleton
    @Provides
    fun providePicturesDao(appDatabase: AppDatabase): PicturesDao =
        appDatabase.getPicturesDao()

    @Singleton
    @Provides
    fun provideAgentDao(appDatabase: AppDatabase): AgentDao =
        appDatabase.getAgentDao()

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Singleton
    @Provides
    fun provideIoExecutor(): Executor = Executors.newSingleThreadExecutor()
}
