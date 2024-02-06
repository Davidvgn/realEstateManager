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
        getPicturesUseCase: GetPicturesUseCase
) : ViewModel() {

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
}
private fun mapItem(picture: PicturesEntity) = PicturesViewStateItem.Pictures(
        id = picture.id,
        image = picture.image,
        description = picture.description
)

private fun mapItemList(picturesEntities: List<PicturesEntity>): List<PicturesViewStateItem.Pictures> {
    return picturesEntities.map { mapItem(it) }
}