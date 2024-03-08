package com.openclassrooms.realestatemanager.ui.real_estates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.convertDollarToEuro
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatPriceForUI
import com.openclassrooms.realestatemanager.data.utils.Utils.Companion.formatPriceWithSpace
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



            if (currency == "Euros") {//todo david changer ne pas mettre en dur
                realEstateEntityList.forEach {

                    if (it.salePrice != "Préciser le prix") { //todo david mieux gérer les placeholders et les répétitions

                        val priceInt = it.salePrice?.toInt()



                        if (it.salePrice != "Préciser le prix") { //todo david mieux gérer les placeholders
                            val price = it.salePrice
                            it.salePrice =
                                price?.let { it1 -> convertDollarToEuro(it1.toInt()) }.toString()

                            it.salePrice = priceInt?.let { formatPriceWithSpace(priceInt) }
                        }
                    }
                }
            } else {
                realEstateEntityList.forEach {
                    if (it.salePrice != "Préciser le prix") { //todo david mieux gérer les placeholders
                        val priceInt = it.salePrice?.toInt()


                        it.salePrice = priceInt?.let { formatPriceForUI(priceInt) }
                    }
                }
            }


            val mappedList = mapItemList(realEstateEntityList, currency)
            if (mappedList.isEmpty()) {
                emit(listOf(RealEstatesViewSateItem.EmptyState))
            } else {
                emit(mappedList)
            }
        }
    }


    private fun mapItem(realEstateEntity: RealEstateEntity, currency: String) =
        RealEstatesViewSateItem.RealEstates(
            id = realEstateEntity.id,
            realEstatesType = realEstateEntity.type,
            photo = realEstateEntity.photo,
            city = realEstateEntity.address,
            salePrice = realEstateEntity.salePrice,
            status = realEstateEntity.status,
            currency = currency
        )

    private fun mapItemList(
        realEstateEntities: List<RealEstateEntity>,
        currency: String
    ): List<RealEstatesViewSateItem.RealEstates> {
        return realEstateEntities.map { mapItem(it, currency) }
    }

}