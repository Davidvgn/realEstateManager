package com.openclassrooms.realestatemanager.data.permissions

import android.Manifest
import android.Manifest.permission
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.domain.permission.PermissionEntity
import com.openclassrooms.realestatemanager.domain.permission.PermissionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PermissionRepositoryImpl
@Inject constructor(
    private val application: Application
) : PermissionRepository {
    override fun getPermissionsFlow(): Flow<List<PermissionEntity>> = flow {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    application, permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    application,
                    permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
                emit(
                    listOf(
                        PermissionEntity(permission.ACCESS_FINE_LOCATION, true),
                        PermissionEntity(permission.ACCESS_COARSE_LOCATION, true)
                    )
                )
        }
    }
}

