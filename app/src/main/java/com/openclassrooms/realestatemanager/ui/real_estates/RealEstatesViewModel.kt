package com.openclassrooms.realestatemanager.ui.real_estates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.domain.real_estates.GetRealEstatesListUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor(
    getRealEstatesListUseCase: GetRealEstatesListUseCase
) : ViewModel() {

    val list: List<RealEstatesViewSateItem> = emptyList()

    val viewStateLiveData: LiveData<List<RealEstatesViewSateItem>> = liveData {
        getRealEstatesListUseCase.invoke().collect { realEstateEntityList ->
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
        city = realEstateEntity.address,
        salePrice = realEstateEntity.salePrice,
        photoUri = realEstateEntity.photo,
        status = realEstateEntity.status
    )

    private fun mapItemList(realEstateEntities: List<RealEstateEntity>): List<RealEstatesViewSateItem.RealEstates> {
        return realEstateEntities.map { mapItem(it) }
    }

}