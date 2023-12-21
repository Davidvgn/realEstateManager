package com.openclassrooms.realestatemanager.domain.permission

import kotlinx.coroutines.flow.Flow

interface PermissionRepository {
    fun getPermissionsFlow() : Flow<List<PermissionEntity>>
    fun refreshPermissions()
}