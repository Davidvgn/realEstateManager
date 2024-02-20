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
        val id: Long,
        val uri: String,
    ): PicturesViewStateItem(Type.PICTURES)

    object EmptyState: PicturesViewStateItem(Type.EMPTY_STATE)
}