package com.openclassrooms.realestatemanager.ui.details

data class DetailViewState(
    val type: String?,
    val salePrice: Int?,
    val floorArea: Int?,
    val numberOfRooms: Int?,
    val description: String?,
    val photo: List<String?>,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: Int?

)