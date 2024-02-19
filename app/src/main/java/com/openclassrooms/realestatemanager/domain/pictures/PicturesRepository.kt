package com.openclassrooms.realestatemanager.domain.pictures

import kotlinx.coroutines.flow.Flow

interface PicturesRepository {
    fun getPicturesAsFlow(): Flow<List<PicturesEntity>>
}