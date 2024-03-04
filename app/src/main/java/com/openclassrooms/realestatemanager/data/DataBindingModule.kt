package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.data.location.LocationRepositoryFused
import com.openclassrooms.realestatemanager.data.permissions.PermissionRepositoryImpl
import com.openclassrooms.realestatemanager.data.pictures.PicturesRepositoryImpl
import com.openclassrooms.realestatemanager.data.real_estates.RealEstateRepositoryRoom
import com.openclassrooms.realestatemanager.domain.details.DetailsRepository
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.permission.PermissionRepository
import com.openclassrooms.realestatemanager.domain.pictures.PicturesRepository
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstatesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingModule {

    @Binds
    @Singleton
    abstract fun bindLocationRepository(impl: LocationRepositoryFused): LocationRepository

    @Binds
    @Singleton
    abstract fun bindPermissionRepository(impl: PermissionRepositoryImpl): PermissionRepository

    @Binds
    @Singleton
    abstract fun bindRealEstateRepository(impl: RealEstateRepositoryRoom): RealEstatesRepository

    @Binds
    @Singleton
    abstract fun bindPicturesRepository(impl: PicturesRepositoryImpl): PicturesRepository

    @Binds
    @Singleton
    abstract fun bindDetailsRepository(impl: DetailsRepositoryImpl): DetailsRepository
}
