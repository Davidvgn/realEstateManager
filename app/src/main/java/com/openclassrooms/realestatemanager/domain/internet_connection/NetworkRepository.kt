package com.openclassrooms.realestatemanager.domain.internet_connection

interface NetworkRepository {
    suspend fun isNetworkAvailable(): Boolean
}
