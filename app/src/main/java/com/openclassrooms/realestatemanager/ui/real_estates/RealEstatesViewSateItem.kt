package com.openclassrooms.realestatemanager.ui.real_estates

import android.net.Uri

data class RealEstatesViewSateItem(
    val id: Int,
    val type: String?,
    val city: String?,
    val salePrice: Int?,
    val photoUri: Uri?,
    val status: String?,
)