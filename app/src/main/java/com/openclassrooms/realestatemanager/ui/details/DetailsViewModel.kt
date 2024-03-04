package com.openclassrooms.realestatemanager.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.details.GetRealEstateByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getRealEstateByIdUseCase: GetRealEstateByIdUseCase,
) : ViewModel() {

    private var realEstateId: Long = -1

    val viewStateLiveData: LiveData<DetailViewState> = liveData {
        getRealEstateByIdUseCase.invoke(realEstateId).collect { realEstate ->
            val realEstateDetails = DetailViewState(
                type = realEstate.type,
                salePrice = realEstate.salePrice,
                floorArea = realEstate.floorArea,
                numberOfRooms = realEstate.numberOfRooms,
                description = realEstate.description,
                address = realEstate.address,
                status = realEstate.status,
                upForSaleDate = realEstate.upForSaleDate,
                dateOfSale = realEstate.dateOfSale,
                realEstateAgent = realEstate.realEstateAgent
            )
            emit(realEstateDetails)
        }
    }

    fun initRealEstateId(id: Long) {
        realEstateId = id
    }

}