package com.openclassrooms.realestatemanager.data.autocomplete

import com.google.gson.annotations.SerializedName

data class AutoCompleteResponse(

    @field:SerializedName("predictions")
    val predictions: List<PredictionsItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)