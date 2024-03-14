package com.openclassrooms.realestatemanager.ui.update

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.utils.Utils
import com.openclassrooms.realestatemanager.domain.agent.GetAgentsUseCase
import com.openclassrooms.realestatemanager.domain.agent.model.AgentEntity
import com.openclassrooms.realestatemanager.domain.currency.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.details.GetPoiListUseCase
import com.openclassrooms.realestatemanager.domain.details.GetRealEstateByIdUseCase
import com.openclassrooms.realestatemanager.domain.pictures.AddPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.AddTemporaryPictureUseCase
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.model.DraftPictureEntity
import com.openclassrooms.realestatemanager.domain.pictures.model.PicturesEntity
import com.openclassrooms.realestatemanager.domain.real_estates.UpdateRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.model.RealEstateEntity
import com.openclassrooms.realestatemanager.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateRealEstateViewModel
    @Inject
    constructor(
        private val getRealEstateByIdUseCase: GetRealEstateByIdUseCase,
        private val getPicturesUseCase: GetPicturesUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
        private val getPoiListUseCase: GetPoiListUseCase,
        private val updateRealEstateUseCase: UpdateRealEstateUseCase,
        private val addTemporaryPictureUseCase: AddTemporaryPictureUseCase,
        private val addPicturesUseCase: AddPicturesUseCase,
        private val getAgentsUseCase: GetAgentsUseCase,
    ) : ViewModel() {
        private var realEstateId: Long = -1
        private var chip: String? = null
        private var address: String? = null
        private var price: String? = null
        private var flourArea: String? = null
        private var description: String? = null
        private var numberOfRooms: String? = null
        private var latLng: LatLng? = null
        private var agentName: String? = null
        private var realEstateStatus: String = "toSale"
        private val poiListMutableLiveData = MutableLiveData<List<String>>()
        private val showToastSingleLiveEventMutableLiveData = MutableLiveData<Event<String>>()
        val showToastSingleLiveEvent: LiveData<Event<String>> = showToastSingleLiveEventMutableLiveData

        private val upForSaleDateChangeMutableLiveData = MutableLiveData<String>()
        var upForSaleDateChangeLiveData: LiveData<String> = upForSaleDateChangeMutableLiveData
        private val onSoldDateChangeMutableLiveData = MutableLiveData<String>()
        val onSoldDateChangeLiveData: LiveData<String> = onSoldDateChangeMutableLiveData
        private val temporaryPicturesMutableLiveData = MutableLiveData<List<DraftPictureEntity>>()

        private val _agentsLiveData = MutableLiveData<List<AgentEntity>>()
        val agentsLiveData: LiveData<List<AgentEntity>> = _agentsLiveData
        private val resourceId: Int = R.drawable.baseline_no_photography_black_36

        private val poiList = poiListMutableLiveData.value?.toMutableList() ?: mutableListOf()
        private var photo: Uri? =
            Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId")

        var creationDate = ""

        val viewStateLiveData: LiveData<UpdateViewState> =
            liveData {
                getRealEstateByIdUseCase.invoke(realEstateId).collect { realEstate ->

                    val currency = getCurrentCurrencyUseCase.invoke()
                    var convertedPrice = realEstate.salePrice

//                    if (realEstate.salePrice != "Préciser le prix") { // todo david mieux gérer les placeholders
//                        if (currency == "€") { // todo david changer ne pas mettre en dur
//                            val price = convertedPrice?.let { Utils.convertDollarToEuro(it.toInt()) }
//                            convertedPrice = price.toString()
//                            convertedPrice = Utils.formatPriceWithSpace(convertedPrice.toInt())
//                        } else {
//                            if (convertedPrice != null) {
//                                convertedPrice = Utils.formatPriceForUI(convertedPrice.toInt())
//                            }
//                        }
//                    }

                    val realEstateDetails =
                        UpdateViewState(
                            id = realEstate.id,
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
                            realEstateAgent = realEstate.realEstateAgent,
                        )
                    emit(realEstateDetails)
                    creationDate = realEstateDetails.creationDate
                    upForSaleDateChangeMutableLiveData.value = realEstate.upForSaleDate.toString()
                    onSoldDateChangeMutableLiveData.value = realEstate.dateOfSale.toString()
                }
            }

        val updateViewStateLiveData: LiveData<RealEstateEntity> =
            liveData {

                val upForSaleDate = upForSaleDateChangeMutableLiveData.value
                val soldDate = onSoldDateChangeMutableLiveData.value

                val updatedRealEstate =
                    RealEstateEntity(
                        id = realEstateId,
                        creationDate = creationDate,
                        type = chip,
                        photo = photo.toString(),
                        salePrice = price,
                        floorArea = flourArea,
                        numberOfRooms = numberOfRooms,
                        description = description,
                        pointOfInterest = poiList,
                        address = address,
                        status = realEstateStatus,
                        upForSaleDate = upForSaleDate,
                        dateOfSale = soldDate,
                        realEstateAgent = agentName,
                        latLng = latLng,
                    )
                updateRealEstateUseCase.invoke(updatedRealEstate)
                emit(updatedRealEstate)
            }

        fun initRealEstateId(id: Long) {
            realEstateId = id
        }

        val poiListLiveData: LiveData<List<String>> =
            liveData {
                getPoiListUseCase.invoke(realEstateId).collect {
                    emit(it)
                }
            }

        val realEstateLocation: LiveData<LatLng> =
            liveData {
                getRealEstateByIdUseCase.invoke(realEstateId).collect {
                    it.latLng?.let { latLng -> emit(latLng) }
                }
            }
        val currentCurrency: LiveData<String> =
            liveData {
                emit(getCurrentCurrencyUseCase())
            }

        private fun showToast() { // todo david ne check que si le bien est présent
            showToastSingleLiveEventMutableLiveData.value =
                Event("Bien Modifié avec succès") // todo david hardcoded text
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

        fun onDateChanged(
            dayOfMonth: Int,
            monthOfYear: Int,
            year: Int,
        ) {
            upForSaleDateChangeMutableLiveData.value = Utils.formatDate(dayOfMonth, monthOfYear, year)
        }

        fun onSoldDateChanged(
            dayOfMonth: Int,
            monthOfYear: Int,
            year: Int,
        ) {
            onSoldDateChangeMutableLiveData.value = Utils.formatDate(dayOfMonth, monthOfYear, year)
        }

        fun addPoi(
            poi: String,
            isCheck: Boolean,
        ) {
            if (isCheck) {
                poiList.add(poi)
            } else {
                poiList.remove(poi)
            }
            poiListMutableLiveData.value = poiList.toList()
        }

        fun updateStatus(status: String) {
            realEstateStatus = status
        }

        fun addTemporaryPicture(
            imageUri: Uri,
            title: String,
        ) {
            val draftPictureEntity =
                DraftPictureEntity(
                    uri = imageUri.toString(),
                    title,
                )

            val tempList = temporaryPicturesMutableLiveData.value?.toMutableList() ?: mutableListOf()
            tempList.add(draftPictureEntity)
            temporaryPicturesMutableLiveData.value = tempList.toList()
            photo = Uri.parse(tempList.first().uri)

            viewModelScope.launch {
                addTemporaryPictureUseCase.invoke(draftPictureEntity)
            }
        }

        private fun updateRealEstateIdForTemporaryPictures(newRealEstateId: Long) {
            val tempList = temporaryPicturesMutableLiveData.value?.toMutableList() ?: return
            tempList.forEach { draftPictureEntity ->
                val pictureEntity = convertToPicturesEntity(draftPictureEntity, newRealEstateId)
                viewModelScope.launch {
                    addPicturesUseCase.invoke(pictureEntity)
                }
            }
        }

        fun addLatLng(
            lat: Double,
            lng: Double,
        ) {
            latLng = LatLng(lat, lng)
        }

        private fun convertToPicturesEntity(
            draftPicture: DraftPictureEntity,
            realEstateId: Long,
        ): PicturesEntity {
            return PicturesEntity(
                id = 0,
                realEstateId = realEstateId,
                uri = draftPicture.uri,
                title = draftPicture.title,
            )
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

        fun onStatusSelected(status: String) {
            realEstateStatus = status
        }
    }
