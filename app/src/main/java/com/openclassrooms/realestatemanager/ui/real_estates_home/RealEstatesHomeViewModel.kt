package com.openclassrooms.realestatemanager.ui.real_estates_home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.domain.real_estates.GetRealEstatesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealEstatesHomeViewModel @Inject constructor(
    getRealEstatesListUseCase: GetRealEstatesListUseCase
) : ViewModel(){


    val isListEmptyLiveData: LiveData<Boolean> = liveData {
        getRealEstatesListUseCase.isListEmpty().collect(){
            emit(it)
        }
    }
}