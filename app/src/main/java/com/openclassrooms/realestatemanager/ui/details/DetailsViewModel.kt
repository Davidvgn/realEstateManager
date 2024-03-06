package com.openclassrooms.realestatemanager.ui.details

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.details.GetRealEstateByIdUseCase
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRealEstateByIdUseCase: GetRealEstateByIdUseCase,
    private val getPicturesUseCase: GetPicturesUseCase,
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
                realEstateAgent = null
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

    fun initRealEstateId(id: Long) {
        realEstateId = id
    }

}