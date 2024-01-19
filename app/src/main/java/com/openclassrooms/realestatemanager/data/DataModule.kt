package com.openclassrooms.realestatemanager.data

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.openclassrooms.realestatemanager.data.pictures.PicturesDao
import com.openclassrooms.realestatemanager.data.realEstates.RealEstateDao
import com.openclassrooms.realestatemanager.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        application: Application
    ): AppDatabase = AppDatabase.create(application)

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.getUserDao()

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
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)
}
