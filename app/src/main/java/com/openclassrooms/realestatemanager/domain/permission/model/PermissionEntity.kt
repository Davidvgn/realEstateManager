package com.openclassrooms.realestatemanager.domain.permission.model

data class PermissionEntity(
    val permission: String,
    val isGranted: Boolean,
)
