package com.openclassrooms.realestatemanager.ui.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.data.utils.Utils
import com.openclassrooms.realestatemanager.domain.currency.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.details.GetPoiListUseCase
import com.openclassrooms.realestatemanager.domain.details.GetRealEstateByIdUseCase
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateRealEstateViewModel
    @Inject
    constructor(
        private val getRealEstateByIdUseCase: GetRealEstateByIdUseCase,
        private val getPicturesUseCase: GetPicturesUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
        private val getPoiListUseCase: GetPoiListUseCase,
    ) : ViewModel() {
        private var realEstateId: Long = -1

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

        val viewStateLiveData: LiveData<UpdateViewState> =
            liveData {

                getRealEstateByIdUseCase.invoke(realEstateId).collect { realEstate ->

                    val currency = getCurrentCurrencyUseCase.invoke()
                    var convertedPrice = realEstate.salePrice

                    if (realEstate.salePrice != "Préciser le prix") { // todo david mieux gérer les placeholders
                        if (currency == "€") { // todo david changer ne pas mettre en dur
                            val price = convertedPrice?.let { Utils.convertDollarToEuro(it.toInt()) }
                            convertedPrice = price.toString()
                            convertedPrice = Utils.formatPriceWithSpace(convertedPrice.toInt())
                        } else {
                            if (convertedPrice != null) {
                                convertedPrice = Utils.formatPriceForUI(convertedPrice.toInt())
                            }
                        }
                    }

                    val realEstateDetails =
                        UpdateViewState(
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
                }
            }

        fun initRealEstateId(id: Long) {
            realEstateId = id
        }
    }
