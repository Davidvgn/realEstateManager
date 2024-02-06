package com.openclassrooms.realestatemanager.ui.addform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatDate
import com.openclassrooms.realestatemanager.domain.real_estates.AddRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFormViewModel @Inject constructor(
        private val addRealEstateUseCase: AddRealEstateUseCase,
) : ViewModel() {

    private var chip: String? = null
    private var address: String? = null
    private var price: String? = null
    private var flourArea: String? = null
    private var description: String? = null

    private val onSaleDateChangeMutableLiveData = MutableLiveData<String>()
    val onSaleDateChangeLiveData: LiveData<String> = onSaleDateChangeMutableLiveData
    private val onSoldDateChangeMutableLiveData = MutableLiveData<String>()
    val onSolDateChangeLiveData: LiveData<String> = onSoldDateChangeMutableLiveData

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
                        address = address ?: "non communiqué",
                        status = "",
                        upForSaleDate = onSaleDateChangeLiveData.toString(),
                        dateOfSale = onSolDateChangeLiveData.toString(),
                        realEstateAgent = null,
                )
        )
    }


    fun onAddressChanged(address: String?) {
        this.address = address
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

    fun onDateChanged(dayOfMonth: Int, monthOfYear: Int, year: Int) {
        onSaleDateChangeMutableLiveData.value = formatDate(dayOfMonth, monthOfYear, year)
    }

    fun onSoldDateChanged(dayOfMonth: Int, monthOfYear: Int, year: Int) {
        onSoldDateChangeMutableLiveData.value = formatDate(dayOfMonth, monthOfYear, year)
    }

}