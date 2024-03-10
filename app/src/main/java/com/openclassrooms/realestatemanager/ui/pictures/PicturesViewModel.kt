package com.openclassrooms.realestatemanager.ui.pictures

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.GetTemporaryPicturesUseCase
import com.openclassrooms.realestatemanager.domain.pictures.draft_picture.DraftPictureEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PicturesViewModel
    @Inject
    constructor(
        private val getPicturesUseCase: GetPicturesUseCase,
        private val getTemporaryPicturesUseCase: GetTemporaryPicturesUseCase,
    ) : ViewModel() {
        val pictureViewStateLiveData: LiveData<List<PicturesViewStateItem>> =
            liveData {
//        getPicturesUseCase.invoke().collect { pictureEntityList ->
//
//            val mappedPicture = mapItemList(pictureEntityList)
//
//            if (mappedPicture.isEmpty()) {
//                emit(listOf(PicturesViewStateItem.EmptyState))
//            } else {
//                emit(mappedPicture)
//            }
//        }

                getTemporaryPicturesUseCase.invoke().collect { pictureEntityList ->

                    val mappedPicture = mapItemList(pictureEntityList)

                    if (mappedPicture.isEmpty()) {
                        emit(listOf(PicturesViewStateItem.EmptyState))
                    } else {
                        emit(mappedPicture)
                    }
                }
            }
    }

private fun mapItem(draftPictureEntity: DraftPictureEntity) =
    PicturesViewStateItem.Pictures(
        id = 0,
        uri = draftPictureEntity.uri,
        title = draftPictureEntity.title,
    )

private fun mapItemList(picturesEntities: List<DraftPictureEntity>): List<PicturesViewStateItem.Pictures> {
    return picturesEntities.map { mapItem(it) }
}
