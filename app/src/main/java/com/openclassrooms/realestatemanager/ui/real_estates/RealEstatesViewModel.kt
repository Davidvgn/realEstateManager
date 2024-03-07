package com.openclassrooms.realestatemanager.ui.real_estates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.data.utils.Utils
import com.openclassrooms.realestatemanager.domain.GetCurrentCurrencyUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.GetRealEstatesListUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
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
                    val priceInt = it.salePrice?.toInt()



                    if (it.salePrice != "Préciser le prix") { //todo david mieux gérer les placeholders
                        val price = it.salePrice
                        it.salePrice =
                            price?.let { it1 -> Utils.convertDollarToEuro(it1.toInt()) }.toString()

                        it.salePrice = priceInt?.let { formatPriceWithSpace(priceInt) }
                    }
                }
            } else {
                realEstateEntityList.forEach {
                    val priceInt = it.salePrice?.toInt()


                    it.salePrice = priceInt?.let { formatPriceForUI(priceInt) }
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


    private fun mapItem(realEstateEntity: RealEstateEntity, currency: String) = RealEstatesViewSateItem.RealEstates(
        id = realEstateEntity.id,
        realEstatesType = realEstateEntity.type,
        photo = realEstateEntity.photo,
        city = realEstateEntity.address,
        salePrice = realEstateEntity.salePrice,
        status = realEstateEntity.status,
        currency = currency
    )

    private fun mapItemList(realEstateEntities: List<RealEstateEntity>, currency: String): List<RealEstatesViewSateItem.RealEstates> {
        return realEstateEntities.map { mapItem(it, currency) }
    }

    private fun formatPriceForUI(price: Int): String {
        val decimalFormat = NumberFormat.getInstance() as DecimalFormat
        return decimalFormat.format(price)
    }

    private fun formatPriceWithSpace(price: Int): String {
        val decimalFormatSymbols = DecimalFormatSymbols.getInstance().apply {
            groupingSeparator = ' ' // Utilisation d'un espace comme séparateur de milliers
        }
        val decimalFormat = DecimalFormat("#,###", decimalFormatSymbols)
        return decimalFormat.format(price)
    }

}