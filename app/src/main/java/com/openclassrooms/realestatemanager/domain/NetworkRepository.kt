package com.openclassrooms.realestatemanager.domain


interface NetworkRepository {
    suspend fun isNetworkAvailable(): Boolean
}
