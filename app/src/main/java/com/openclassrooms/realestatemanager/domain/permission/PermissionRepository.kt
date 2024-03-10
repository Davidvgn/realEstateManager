package com.openclassrooms.realestatemanager.domain.permission

import com.openclassrooms.realestatemanager.domain.permission.model.PermissionEntity
import kotlinx.coroutines.flow.Flow

interface PermissionRepository {
    fun getPermissionsFlow(): Flow<List<PermissionEntity>>

    fun refreshPermissions()
}
