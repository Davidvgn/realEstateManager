package com.openclassrooms.realestatemanager.ui.real_estates

sealed class RealEstatesViewSateItem(
    val type: Type,
) {
    enum class Type {
        REAL_ESTATE,
        EMPTY_STATE,
    }

    data class RealEstates(
        val id: Long,
        val realEstatesType: String?,
        val photo: String?,
        val city: String?,
        val salePrice: String?,
        val status: String,
        val currency: String?,
    ) : RealEstatesViewSateItem(Type.REAL_ESTATE)

    object EmptyState : RealEstatesViewSateItem(Type.EMPTY_STATE)
}
