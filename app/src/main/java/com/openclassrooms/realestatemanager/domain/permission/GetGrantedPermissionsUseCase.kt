package com.openclassrooms.realestatemanager.domain.permission

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGrantedPermissionsUseCase @Inject constructor(
    private val permissionRepository: PermissionRepository,
) {
    fun invoke(): Flow<List<PermissionEntity>> = permissionRepository.getPermissionsFlow()
}