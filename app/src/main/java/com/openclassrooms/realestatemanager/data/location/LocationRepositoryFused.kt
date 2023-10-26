package com.openclassrooms.realestatemanager.data.location

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.openclassrooms.realestatemanager.domain.location.LocationRepository
import com.openclassrooms.realestatemanager.domain.location.model.LocationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class LocationRepositoryFused @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
) : LocationRepository {

    companion object {
        private val GPS_LOCATION_INTERVAL_DURATION = 10.seconds
    }

    @SuppressLint("MissingPermission")
    override fun getLocationAsFlow(): Flow<LocationEntity> = callbackFlow {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, GPS_LOCATION_INTERVAL_DURATION.inWholeMilliseconds)
            .setMinUpdateDistanceMeters(50F)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    trySend(
                        LocationEntity(
                            lat = it.latitude,
                            long = it.longitude,
                        )
                    )
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, Dispatchers.IO.asExecutor(), locationCallback)

        awaitClose { fusedLocationClient.removeLocationUpdates(locationCallback) }
    }
}