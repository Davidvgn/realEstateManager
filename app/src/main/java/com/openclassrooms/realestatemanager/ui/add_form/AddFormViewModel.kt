package com.openclassrooms.realestatemanager.ui.add_form

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.utils.Utils
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatDate
import com.openclassrooms.realestatemanager.domain.agent.AgentEntity
import com.openclassrooms.realestatemanager.domain.agent.GetAgentsUseCase
import com.openclassrooms.realestatemanager.domain.pictures.AddPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.AddTemporaryPictureUseCase
import com.openclassrooms.realestatemanager.domain.pictures.DeleteTemporaryPicturesListUseCase
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.real_estates.AddRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFormViewModel @Inject constructor(
    private val addRealEstateUseCase: AddRealEstateUseCase,
    private val addTemporaryPictureUseCase: AddTemporaryPictureUseCase,
    private val addPicturesUseCase: AddPicturesUseCase,
    private val deleteTemporaryPicturesListUseCase: DeleteTemporaryPicturesListUseCase,
    private val getAgentsUseCase: GetAgentsUseCase
) : ViewModel() {

    private var chip: String? = null
    private var address: String? = null
    private var price: String? = null
    private var flourArea: String? = null
    private var description: String? = null
    private var numberOfRooms: String? = null
    private var latLng: LatLng? = null
    private var agentName: String? = null

    private val _agentsLiveData = MutableLiveData<List<AgentEntity>>()
    val agentsLiveData: LiveData<List<AgentEntity>> = _agentsLiveData


    private val resourceId: Int = R.drawable.baseline_no_photography_black_36
    private var photo: Uri? =
        Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId")

    private val _temporaryPictures = MutableLiveData<List<PicturesEntity>>()
    private val _poiList = MutableLiveData<List<String>>()
    val poiList = _poiList.value?.toMutableList() ?: mutableListOf()


    private val onSaleDateChangeMutableLiveData = MutableLiveData<String>()
    val onSaleDateChangeLiveData: LiveData<String> = onSaleDateChangeMutableLiveData
    private val onSoldDateChangeMutableLiveData = MutableLiveData<String>()
    val onSoldDateChangeLiveData: LiveData<String> = onSoldDateChangeMutableLiveData

    //todo david texte en dur
    //todo david : viewModelScope doit launch le AddRealEstateViewState et non créer un realEstateEntity
    val viewStateAddRealEstateLiveData: LiveData<AddRealEstateViewState> = liveData {
        val newRealEstate = RealEstateEntity(
            creationDate = Utils.getTodayDate(),
            type = chip ?: "Préciser le type",
            salePrice = price ?: "Préciser le prix",
            photo = photo.toString(),
            floorArea = flourArea ?: "Préciser la surface",
            numberOfRooms = numberOfRooms ?: "Préciser nombre de p!èces",
            description = description ?: "Ajouter une description",
            address = address ?: "Précisez l'adresse",
            pointOfInterest = poiList,
            status = "",
            upForSaleDate = onSaleDateChangeLiveData.toString(),
            dateOfSale = onSoldDateChangeLiveData.toString(),
            realEstateAgent = agentName.toString(),
            latLng = latLng
        )
        viewModelScope.launch {
            val id = addRealEstateUseCase.invoke(realEstate = newRealEstate)
            updateRealEstateIdForTemporaryPictures(id)
            deleteTemporaryPicturesListUseCase.invoke()
        }
    }


    fun onNumberOfRoomsChanged(numberOfRooms: String?) {
        this.numberOfRooms = numberOfRooms
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

    fun addPoi(poi: String, isCheck: Boolean) {
        if (isCheck) {
            poiList.add(poi)
        } else {
            poiList.remove(poi)
        }
        _poiList.value = poiList.toList()
    }

    fun addTemporaryPictureFromGallery(imageUri: Uri) {
        val pictureEntity = PicturesEntity(
            id = 0,
            realEstateId = null,
            uri = imageUri.toString(),
        )

        val tempList = _temporaryPictures.value?.toMutableList() ?: mutableListOf()
        tempList.add(pictureEntity)
        _temporaryPictures.value = tempList.toList()
        photo = Uri.parse(tempList.first().uri)

        viewModelScope.launch {
            addTemporaryPictureUseCase.invoke(pictureEntity)

        }
    }

    private fun updateRealEstateIdForTemporaryPictures(newRealEstateId: Long) {
        val tempList = _temporaryPictures.value?.toMutableList() ?: return
        tempList.forEach { it.realEstateId = newRealEstateId }
        _temporaryPictures.value = tempList.toList()
        viewModelScope.launch {

            tempList.forEach {
                addPicturesUseCase.invoke(it)
            }
        }
    }

    fun addLatLng(lat: Double, lng: Double) {
        latLng = LatLng(lat, lng)
    }

    fun onAgentSelected(name: String) {
        this.agentName = name

    }

    fun fetchAgents() {
        viewModelScope.launch {
            val agents = getAgentsUseCase()
            _agentsLiveData.postValue(agents)
        }
    }


}