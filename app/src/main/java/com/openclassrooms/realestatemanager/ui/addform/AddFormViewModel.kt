package com.openclassrooms.realestatemanager.ui.addform

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.real_estates.AddRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFormViewModel @Inject constructor(
        private val addRealEstateUseCase: AddRealEstateUseCase,
) : ViewModel() {

    private var chip: String? = null
    private var saleDate: String? = null
    private var soldDate: String? = null
    private var price: String? = null
    private var flourArea: String? = null
    private var description: String? = null

    //todo david texte en dur
    val viewStateAddRealEstateLiveData: LiveData<AddRealEstateViewState> = liveData {
        addRealEstateUseCase.invoke(
                realEstate = RealEstateEntity(
                        type = chip ?: "non communiqué",
                        salePrice = price ?: "non communiqué",
                        floorArea = flourArea ?: "non communiqué",
                        numberOfRooms = 4,
                        description = description ?: "non communiqué",
                        photo = "",
                        address = "Lyon",
                        status = "",
                        upForSaleDate = saleDate,
                        dateOfSale = soldDate,
                        realEstateAgent = null,
                )
        )
    }

    fun onTextPriceChanged(price: String?) {
        this.price = price
    }

    fun onTextFloorAreaChanged(floorArea: String?) {
        this.flourArea = floorArea
    }

    fun onTextDescriptionChanged(description: String?) {
        this.description = description
    }

    fun onTypeChanged(chip: String) {
        this.chip = chip
    }

    fun onDateChanged(day: String, month: String, year: String) {
        this.saleDate = ("$day/${month}/$year")
    }

    fun onSoldDateChanged(day: String, month: String, year: String) {
        this.soldDate = ("$day/${month}/$year")
    }

}