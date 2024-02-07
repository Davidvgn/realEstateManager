package com.openclassrooms.realestatemanager.ui.add_form

data class AddRealEstateViewState(
    val type: String?,
    val salePrice: Int?,
    val floorArea: Int?,
    val numberOfRooms: Int?,
    val description: String?,
    val photo: String?,
    val address: String?,
    val status: String?,
    val upForSaleDate: String?,
    val dateOfSale: String?,
    val realEstateAgent: Int?
) {
}