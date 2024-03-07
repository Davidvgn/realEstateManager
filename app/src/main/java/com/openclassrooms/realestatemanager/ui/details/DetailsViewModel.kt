package com.openclassrooms.realestatemanager.ui.details

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.utils.Utils
import com.openclassrooms.realestatemanager.domain.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.details.GetRealEstateByIdUseCase
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRealEstateByIdUseCase: GetRealEstateByIdUseCase,
    private val getPicturesUseCase: GetPicturesUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase
) : ViewModel() {

    private var realEstateId: Long = -1
    private val resourceId: Int = R.drawable.baseline_no_photography_black_36
    private var photo: Uri? = Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId")

    val realEstateLocation: LiveData<LatLng> = liveData {
        getRealEstateByIdUseCase.invoke(realEstateId).collect{
            it.latLng?.let { latLng -> emit(latLng) }
        }
    }


    val viewStateLiveData: LiveData<DetailViewState> = liveData {
        getRealEstateByIdUseCase.invoke(realEstateId).collect { realEstate ->

            val currency = getCurrentCurrencyUseCase.invoke()
            val priceInt = realEstate.salePrice?.toInt()

            if (currency == "Euros"){//todo david changer ne pas mettre en dur

                val price = realEstate.salePrice
                realEstate.salePrice = price?.let { it1 -> Utils.convertDollarToEuro(it1.toInt()) }.toString()

                realEstate.salePrice = priceInt?.let { formatPriceWithSpace(priceInt) }

            } else {
                realEstate.salePrice = priceInt?.let { formatPriceForUI(priceInt) }
            }

            val realEstateDetails = DetailViewState(
                creationDate = realEstate.creationDate,
                type = realEstate.type,
                salePrice = realEstate.salePrice,
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
        getPicturesUseCase.invoke(realEstateId).collect{
            it.forEach{pictureEntity ->
                emit(pictureEntity.uri)
            }

        }
    }

    fun formatPriceForUI(price: Int): String {
        val decimalFormat = NumberFormat.getInstance() as DecimalFormat
        return decimalFormat.format(price)
    }

    fun formatPriceWithSpace(price: Int): String {
        val decimalFormatSymbols = DecimalFormatSymbols.getInstance().apply {
            groupingSeparator = ' ' // Utilisation d'un espace comme séparateur de milliers
        }
        val decimalFormat = DecimalFormat("#,###", decimalFormatSymbols)
        return decimalFormat.format(price)
    }



    fun initRealEstateId(id: Long) {
        realEstateId = id
    }

}