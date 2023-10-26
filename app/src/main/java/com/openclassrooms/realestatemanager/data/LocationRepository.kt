package com.openclassrooms.realestatemanager.data

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private var fusedLocationClient: FusedLocationProviderClient?) {

    private val TAG = "DAVID"


    fun startLocationRequest(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )

        }
        fusedLocationClient!!.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.d(TAG, "getLastLocation : latitude is ${location.latitude}")
                    Log.d(TAG, "getLastLocation : longitude is ${location.longitude}")
                } else {
                    Log.d(TAG, "location is null}")

                }
            }
            .addOnFailureListener {
                Log.d(TAG, "getLastLocation : Location cannot be traced")
            }

    }


}