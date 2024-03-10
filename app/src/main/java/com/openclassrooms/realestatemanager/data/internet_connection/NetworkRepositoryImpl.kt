package com.openclassrooms.realestatemanager.data.internet_connection

import android.content.Context
import android.net.ConnectivityManager
import com.openclassrooms.realestatemanager.domain.internet_connection.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl
    @Inject
    constructor(private val context: Context) : NetworkRepository {
        override suspend fun isNetworkAvailable(): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
