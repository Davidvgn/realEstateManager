package com.openclassrooms.realestatemanager.data.permissions

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.openclassrooms.realestatemanager.domain.permission.PermissionRepository
import com.openclassrooms.realestatemanager.domain.permission.model.PermissionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PermissionRepositoryImpl
    @Inject
    constructor(
        private val application: Application,
    ) : PermissionRepository {
        companion object {
            private val availablePermissions =
                listOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
        }

        private val permissionsMutableSharedFlow: MutableStateFlow<List<PermissionEntity>> =
            MutableStateFlow(computePermissions())

        override fun getPermissionsFlow(): Flow<List<PermissionEntity>> = permissionsMutableSharedFlow

        override fun refreshPermissions() {
            permissionsMutableSharedFlow.value = computePermissions()
        }

        private fun computePermissions(): List<PermissionEntity> =
            availablePermissions.map { permission ->
                PermissionEntity(permission, isPermissionGranted(permission))
            }

        private fun isPermissionGranted(permission: String): Boolean =
            ContextCompat.checkSelfPermission(
                application,
                permission,
            ) == PackageManager.PERMISSION_GRANTED
    }
