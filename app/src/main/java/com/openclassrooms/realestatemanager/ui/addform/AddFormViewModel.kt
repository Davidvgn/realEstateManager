package com.openclassrooms.realestatemanager.ui.addform

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.pictures.GetPicturesUseCase
import com.openclassrooms.realestatemanager.ui.pictures.PicturesViewStateItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddFormViewModel @Inject constructor(
    getPicturesUseCase: GetPicturesUseCase
): ViewModel() {

    val viewStateLiveData: LiveData<List<PicturesViewStateItem>> = liveData {
        getPicturesUseCase.invoke()
        emit(listOf(PicturesViewStateItem.EmptyState))
    }
}