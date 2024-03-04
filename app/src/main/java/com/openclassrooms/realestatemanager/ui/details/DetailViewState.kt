package com.openclassrooms.realestatemanager.ui.details

data class DetailViewState(
    val type: String?,
    val salePrice: String?,
    val floorArea: String?,
    val numberOfRooms: Int?,
    val description: String?,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: Int?

)