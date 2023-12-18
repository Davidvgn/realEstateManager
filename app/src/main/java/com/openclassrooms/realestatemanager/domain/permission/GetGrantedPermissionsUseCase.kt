package com.openclassrooms.realestatemanager.domain.permission

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetGrantedPermissionsUseCase @Inject constructor(
    private val permissionRepository: PermissionRepository,
) {
     fun invoke(): Flow<List<PermissionEntity>> = permissionRepository.getPermissionsFlow().filter { permission ->
        permission.any {
            it.permission == "android.permission.ACCESS_FINE_LOCATION"
                    || it.permission == "android.permission.ACCESS_COARSE_LOCATION"
        }
    }

}