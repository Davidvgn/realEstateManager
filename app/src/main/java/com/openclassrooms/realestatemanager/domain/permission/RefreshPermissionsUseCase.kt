package com.openclassrooms.realestatemanager.domain.permission

import javax.inject.Inject

class RefreshPermissionsUseCase @Inject constructor(
    private val permissionRepository: PermissionRepository,
) {
    fun invoke() {
        permissionRepository.refreshPermissions()
    }
}