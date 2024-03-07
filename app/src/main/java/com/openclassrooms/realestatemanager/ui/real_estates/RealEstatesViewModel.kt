package com.openclassrooms.realestatemanager.ui.real_estates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.data.utils.Utils
import com.openclassrooms.realestatemanager.domain.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.GetRealEstatesListUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor(
    getRealEstatesListUseCase: GetRealEstatesListUseCase,
    private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase

) : ViewModel() {

    val viewStateLiveData: LiveData<List<RealEstatesViewSateItem>> = liveData {


        getRealEstatesListUseCase.invoke().collect { realEstateEntityList ->

            val currency = getCurrentCurrencyUseCase.invoke()
            if (currency == "Euros"){//todo david changer ne pas mettre en dur
                realEstateEntityList.forEach {
                    if (it.salePrice != "Préciser le prix") {
                        val price = it.salePrice
                        it.salePrice =
                            price?.let { it1 -> Utils.convertDollarToEuro(it1.toInt()) }.toString()
                    }
                }
            }


            val mappedList = mapItemList(realEstateEntityList)
            if (mappedList.isEmpty()) {
                emit(listOf(RealEstatesViewSateItem.EmptyState))
            } else {
                emit(mappedList)
            }
        }
    }


    private fun mapItem(realEstateEntity: RealEstateEntity) = RealEstatesViewSateItem.RealEstates(
        id = realEstateEntity.id,
        realEstatesType = realEstateEntity.type,
        photo = realEstateEntity.photo,
        city = realEstateEntity.address,
        salePrice = realEstateEntity.salePrice,
        status = realEstateEntity.status
    )

    private fun mapItemList(realEstateEntities: List<RealEstateEntity>): List<RealEstatesViewSateItem.RealEstates> {
        return realEstateEntities.map { mapItem(it) }
    }

}