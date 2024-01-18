package com.openclassrooms.realestatemanager.domain.pictures

import kotlinx.coroutines.flow.Flow

interface PicturesRespository {
    fun getPicturesAsFlow(): Flow<List<PicturesEntity>>
}