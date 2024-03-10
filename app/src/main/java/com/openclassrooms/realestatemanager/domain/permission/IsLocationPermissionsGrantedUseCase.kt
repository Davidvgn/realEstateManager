package com.openclassrooms.realestatemanager.domain.permission

import android.Manifest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsLocationPermissionsGrantedUseCase
    @Inject
    constructor(
        private val permissionRepository: PermissionRepository,
    ) {
        fun invoke(): Flow<Boolean> =
            permissionRepository.getPermissionsFlow().map { permissions ->
                permissions.any {
                    it.permission == Manifest.permission.ACCESS_FINE_LOCATION ||
                        it.permission == Manifest.permission.ACCESS_COARSE_LOCATION
                }
            }.distinctUntilChanged()
    }
