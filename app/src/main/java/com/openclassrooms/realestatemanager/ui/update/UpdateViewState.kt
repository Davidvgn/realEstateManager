package com.openclassrooms.realestatemanager.ui.update

data class UpdateViewState(
    val creationDate: String?,
    val type: String?,
    val salePrice: String?,
    val floorArea: String?,
    val numberOfRooms: String?,
    val description: String?,
    val address: String?,
    val status: String?,
    val poi: List<String?>,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: String?,
)
