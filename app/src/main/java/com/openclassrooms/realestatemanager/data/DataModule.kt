package com.openclassrooms.realestatemanager.data

import android.app.Application
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.openclassrooms.realestatemanager.data.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context?): FusedLocationProviderClient? {
        return LocationServices.getFusedLocationProviderClient(context!!)
    }
}
