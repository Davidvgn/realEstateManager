package com.openclassrooms.realestatemanager.ui.addform

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import com.openclassrooms.realestatemanager.domain.real_estates.AddRealEstateUseCase
import com.openclassrooms.realestatemanager.domain.real_estates.RealEstateEntity
import com.openclassrooms.realestatemanager.ui.pictures.PicturesViewStateItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFormViewModel @Inject constructor(
    private val addRealEstateUseCase: AddRealEstateUseCase,
    getPicturesUseCase: GetPicturesUseCase
) : ViewModel() {

    private var description: String? = null

    val pictureViewStateLiveData: LiveData<List<PicturesViewStateItem>> = liveData {
        getPicturesUseCase.invoke().collect { pictureEntityList ->

            val mappedPicture = mapItemList(pictureEntityList)

            if (mappedPicture.isEmpty()) {
                emit(listOf(PicturesViewStateItem.EmptyState))
            } else {
                emit(mappedPicture)

            }
        }
    }

    val viewStateAddRealEstateLiveData: LiveData<AddRealEstateViewState> = liveData {
        addRealEstateUseCase.invoke(
            realEstate = RealEstateEntity(
                type = "TEST1",
                salePrice = 40000,
                floorArea = 12,
                numberOfRooms = 4,
                description = description,
                photo = "",
                address = "",
                status = "",
                upForSaleDate = "",
                dateOfSale = "",
                realEstateAgent = null,
            )
        )
    }

    private fun mapItem(picture: PicturesEntity) = PicturesViewStateItem.Pictures(
        id = picture.id,
        image = picture.image,
        description = picture.description
    )

    private fun mapItemList(picturesEntities: List<PicturesEntity>): List<PicturesViewStateItem.Pictures> {
        return picturesEntities.map { mapItem(it) }
    }

    fun onTextDescriptionChanged(description: String?) {
        this.description = description
    }
}