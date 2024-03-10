package com.openclassrooms.realestatemanager.domain.permission

data class PermissionEntity(
    val permission: String,
    val isGranted: Boolean,
)
