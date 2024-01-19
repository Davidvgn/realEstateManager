package com.openclassrooms.realestatemanager.ui.pictures

sealed class PicturesViewStateItem(
    val type: Type

) {

    enum class Type {
        PICTURES,
        EMPTY_STATE
    }


    data class Pictures
        (
        val id: Int,
        val image: String,
        val description: String
    ): PicturesViewStateItem(Type.PICTURES)

    object EmptyState: PicturesViewStateItem(Type.EMPTY_STATE)
}