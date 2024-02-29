package com.openclassrooms.realestatemanager.ui.pictures

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.PicturesEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PicturesViewModel @Inject constructor(
       private val getPicturesUseCase: GetPicturesUseCase
) : ViewModel() {

    var realEstateId = 0L

    val pictureViewStateLiveData: LiveData<List<PicturesViewStateItem>> = liveData {
        getPicturesUseCase.invoke(realEstateId).collect { pictureEntityList ->

            val mappedPicture = mapItemList(pictureEntityList)

            if (mappedPicture.isEmpty()) {
                emit(listOf(PicturesViewStateItem.EmptyState))
            } else {
                emit(mappedPicture)
            }
        }
    }

    fun getId(id: Long) {
        realEstateId = id
    }

}
private fun mapItem(picture: PicturesEntity) = PicturesViewStateItem.Pictures(
        id = picture.id,
        uri = picture.uri,
)

private fun mapItemList(picturesEntities: List<PicturesEntity>): List<PicturesViewStateItem.Pictures> {
    return picturesEntities.map { mapItem(it) }
}