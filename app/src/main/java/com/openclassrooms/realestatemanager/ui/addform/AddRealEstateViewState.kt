package com.openclassrooms.realestatemanager.ui.addform

data class AddRealEstateViewState(
    val id: Int,
    val type: String?,
    val salePrice: Int?,
    val floorArea: Int?,
    val numberOfRooms: Int?,
    val description: String?,
    val Photo: String?,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: Int?
) {
}