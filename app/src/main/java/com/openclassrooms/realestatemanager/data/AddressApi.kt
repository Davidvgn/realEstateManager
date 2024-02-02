package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.data.autocomplete.AutoCompleteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressApi {

    @GET("place/autocomplete/json")
    suspend fun getAddressPredictions(
        @Query("input") input: String,
        @Query("type") type: String
    ): AutoCompleteResponse

}