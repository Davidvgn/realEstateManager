package com.openclassrooms.realestatemanager.ui.add_form

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatDate
import com.openclassrooms.realestatemanager.domain.pictures.AddPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.real_estates.AddRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFormViewModel @Inject constructor(
    private val addRealEstateUseCase: AddRealEstateUseCase,
    private val addPicturesUseCase: AddPicturesUseCase
) : ViewModel() {

    private var chip: String? = null
    private var address: String? = null
    private var price: String? = null
    private var flourArea: String? = null
    private var description: String? = null

    private val _newRealEstateId = MutableLiveData<Long>()
    val newRealEstateId: LiveData<Long> = _newRealEstateId

    private val onSaleDateChangeMutableLiveData = MutableLiveData<String>()
    val onSaleDateChangeLiveData: LiveData<String> = onSaleDateChangeMutableLiveData
    private val onSoldDateChangeMutableLiveData = MutableLiveData<String>()
    val onSoldDateChangeLiveData: LiveData<String> = onSoldDateChangeMutableLiveData



    val viewStateRealEstateLiveData: LiveData<AddRealEstateViewState> = liveData {
        val newRealEstate = RealEstateEntity(
            type = null,
            salePrice = null,
            floorArea = null,
            numberOfRooms = null,
            description = null,
            photo = null,
            address = null,
            status = null,
            upForSaleDate = null,
            dateOfSale = null,
            realEstateAgent = null
        )
        viewModelScope.launch {
            val id = addRealEstateUseCase.invoke(realEstate = newRealEstate)
            _newRealEstateId.value = id
        }
    }

    //todo david texte en dur
    val viewStateAddRealEstateLiveData: LiveData<AddRealEstateViewState> = liveData {
        val newRealEstateId = _newRealEstateId.value ?: 0L

        addRealEstateUseCase.invoke(
            realEstate = RealEstateEntity(
                id = newRealEstateId,
                type = chip ?: "Préciser le type",
                salePrice = price ?: "Préciser le prix",
                floorArea = flourArea ?: "Préciser la surface",
                numberOfRooms = 4,
                description = description ?: "Ajouter une description",
                photo = "",
                address = address ?: "Précisez l'adresse",
                status = "",
                upForSaleDate = onSaleDateChangeLiveData.toString(),
                dateOfSale = onSoldDateChangeLiveData.toString(),
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

    fun addPictureFromGallery(imageUri: Uri) {
        val newRealEstateId = _newRealEstateId.value
        if (newRealEstateId != null) {
            val pictureEntity = PicturesEntity(
                id = 0,
                realEstateId = newRealEstateId,
                uri = imageUri.toString(),
            )
            viewModelScope.launch {
                addPicturesUseCase.invoke(pictureEntity)
            }
        } else {
            throw IllegalStateException("Real estate ID is null")
        }
    }

}