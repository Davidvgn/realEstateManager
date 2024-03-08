package com.openclassrooms.realestatemanager.ui.details

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.convertDollarToEuro
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatPriceForUI
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatPriceWithSpace
import com.openclassrooms.realestatemanager.domain.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.details.GetRealEstateByIdUseCase
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRealEstateByIdUseCase: GetRealEstateByIdUseCase,
    private val getPicturesUseCase: GetPicturesUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase
) : ViewModel() {

    private var realEstateId: Long = -1
    private val resourceId: Int = R.drawable.baseline_no_photography_black_36
    private var photo: Uri? =
        Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId")

    val realEstateLocation: LiveData<LatLng> = liveData {
        getRealEstateByIdUseCase.invoke(realEstateId).collect {
            it.latLng?.let { latLng -> emit(latLng) }
        }
    }
    val currentCurrency: LiveData<String> = liveData {
        emit(getCurrentCurrencyUseCase())
    }

    val viewStateLiveData: LiveData<DetailViewState> = liveData {

        getRealEstateByIdUseCase.invoke(realEstateId).collect { realEstate ->

            val currency = getCurrentCurrencyUseCase.invoke()
            var convertedPrice = realEstate.salePrice

            if (realEstate.salePrice != "Préciser le prix") {                //todo david mieux gérer les placeholders
                if (currency == "Euros") {                                  //todo david changer ne pas mettre en dur
                    val price = convertedPrice?.let { convertDollarToEuro(it.toInt()) }
                    convertedPrice = price.toString()
                    convertedPrice = formatPriceWithSpace(convertedPrice.toInt())
                } else {
                    if (convertedPrice != null) {
                        convertedPrice = formatPriceForUI(convertedPrice.toInt())
                    }
                }
            }


            val realEstateDetails = DetailViewState(
                creationDate = realEstate.creationDate,
                type = realEstate.type,
                salePrice = convertedPrice,
                floorArea = realEstate.floorArea,
                numberOfRooms = realEstate.numberOfRooms,
                description = realEstate.description,
                address = realEstate.address,
                status = realEstate.status,
                poi = realEstate.pointOfInterest,
                upForSaleDate = realEstate.upForSaleDate,
                dateOfSale = realEstate.dateOfSale,
                realEstateAgent = realEstate.realEstateAgent
            )
            emit(realEstateDetails)
        }

    }

    val realEstatePictures = liveData {
        getPicturesUseCase.invoke(realEstateId).collect {
            it.forEach { pictureEntity ->
                emit(pictureEntity.uri)
            }

        }
    }


    fun initRealEstateId(id: Long) {
        realEstateId = id
    }

}