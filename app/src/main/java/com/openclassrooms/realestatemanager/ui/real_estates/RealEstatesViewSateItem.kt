package com.openclassrooms.realestatemanager.ui.real_estates

import android.net.Uri


sealed class RealEstatesViewSateItem(
    val type: Type,
) {
    enum class Type {
        REAL_ESTATE,
        EMPTY_STATE,
    }

    data class RealEstates(
        val id: Int,
        val realEstatesType: String?,
        val city: String?,
        val salePrice: Int?,
        val photoUri: Uri?,
        val status: String?,
    ) : RealEstatesViewSateItem(Type.REAL_ESTATE)

    object EmptyState : RealEstatesViewSateItem(Type.EMPTY_STATE)
}