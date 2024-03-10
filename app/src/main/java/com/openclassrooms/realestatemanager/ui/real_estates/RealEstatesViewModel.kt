package com.openclassrooms.realestatemanager.ui.real_estates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.convertDollarToEuro
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatPriceForUI
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatPriceWithSpace
import com.openclassrooms.realestatemanager.domain.currency.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.GetRealEstatesListUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.model.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel
    @Inject
    constructor(
        getRealEstatesListUseCase: GetRealEstatesListUseCase,
        private val getCurrentCurrencyUseCase: GetCurrentCurrencyUseCase,
    ) : ViewModel() {
        val viewStateLiveData: LiveData<List<RealEstatesViewSateItem>> =
            liveData {

                getRealEstatesListUseCase.invoke().collect { realEstateEntityList ->

                    val currency = getCurrentCurrencyUseCase.invoke()

                    val mappedList = mapItemList(realEstateEntityList, currency)
                    if (mappedList.isEmpty()) {
                        emit(listOf(RealEstatesViewSateItem.EmptyState))
                    } else {
                        emit(mappedList)
                    }
                }
            }

        private fun mapItem(
            realEstateEntity: RealEstateEntity,
            currency: String,
        ): RealEstatesViewSateItem.RealEstates {
            val convertedRealEstate = convertRealEstateEntity(realEstateEntity, currency)

            return RealEstatesViewSateItem.RealEstates(
                id = convertedRealEstate.id,
                realEstatesType = convertedRealEstate.type,
                photo = convertedRealEstate.photo,
                city = convertedRealEstate.address,
                salePrice = convertedRealEstate.salePrice,
                status = convertedRealEstate.status,
                currency = currency,
            )
        }

        private fun mapItemList(
            realEstateEntities: List<RealEstateEntity>,
            currency: String,
        ): List<RealEstatesViewSateItem.RealEstates> {
            return realEstateEntities.map { mapItem(it, currency) }
        }
    }

private fun convertRealEstateEntity(
    realEstateEntity: RealEstateEntity,
    currency: String,
): RealEstateEntity {
    if (realEstateEntity.salePrice != "Préciser le prix") {
        val convertedSalePrice =
            when {
                currency == "€" -> {
                    realEstateEntity.salePrice?.toIntOrNull()?.let { convertDollarToEuro(it) }
                        ?.toString() ?: ""
                }

                else -> {
                    realEstateEntity.salePrice
                }
            }?.let { if (currency == "€") formatPriceWithSpace(it.toInt()) else formatPriceForUI(it.toInt()) }
                ?: ""

        return realEstateEntity.copy(salePrice = convertedSalePrice)
    }
    return realEstateEntity
}
