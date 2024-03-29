package com.openclassrooms.realestatemanager.ui.details

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.domain.currency.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.details.GetPoiListUseCase
import com.openclassrooms.realestatemanager.domain.details.GetRealEstateByIdUseCase
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import com.openclassrooms.realestatemanager.ui.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
    @Inject
    constructor(
        private val getRealEstateByIdUseCase: GetRealEstateByIdUseCase,
        private val getPicturesUseCase: GetPicturesUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
        private val getPoiListUseCase: GetPoiListUseCase,
        private val application: Application,
    ) : ViewModel() {
        private var realEstateId: Long = -1
        private val resourceId: Int = R.drawable.baseline_no_photography_black_36
        private var photo: Uri? =
            Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId")

        private val statusMutableLiveData = MutableLiveData<String?>()
        private val statusLiveData: MutableLiveData<String?> = statusMutableLiveData

        private val soldDateMutableLiveData = MutableLiveData<String?>()
        private val soldDateLiveData: MutableLiveData<String?> = soldDateMutableLiveData

        private val showToastSingleLiveEventMutableLiveData = MutableLiveData<Event<String>>()
        val showToastSingleLiveEvent: LiveData<Event<String>> = showToastSingleLiveEventMutableLiveData

        val viewStateLiveData: LiveData<DetailViewState> =
            liveData {

                getRealEstateByIdUseCase.invoke(realEstateId).collect { realEstate ->

                    val currency = getCurrentCurrencyUseCase.invoke()
                    var convertedPrice = realEstate.salePrice

                    statusMutableLiveData.value = realEstate.status
                    soldDateLiveData.value = realEstate.dateOfSale

//                    if (realEstate.salePrice != "Préciser le prix") { // todo david mieux gérer les placeholders
//                        if (currency == "€") { // todo david changer ne pas mettre en dur
//                            val price = convertedPrice?.let { convertDollarToEuro(it.toInt()) }
//                            convertedPrice = price.toString()
//                            convertedPrice = formatPriceWithSpace(convertedPrice.toInt())
//                        } else {
//                            if (convertedPrice != null) {
//                                convertedPrice = formatPriceForUI(convertedPrice.toInt())
//                            }
//                        }
//                    }

                    val realEstateDetails =
                        DetailViewState(
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
                    showToast()
                }
            }

        val realEstatePictures =
            liveData {
                getPicturesUseCase.invoke(realEstateId).collect {
                    it.forEach { pictureEntity ->
                        emit(pictureEntity.uri)
                    }
                }
            }

        fun getRealEstateStatus(): String? {
            return statusLiveData.value
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

        private fun showToast() {
            showToastSingleLiveEventMutableLiveData.value =
                Event(application.getString(R.string.archived_property))
        }
    }
