package com.openclassrooms.realestatemanager.ui.realEstates

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.ui.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor() : ViewModel() {

    val singleLiveEvent = SingleLiveEvent<RealEstatesEvent>()
    fun onAddButtonClicked() {
        singleLiveEvent.value = RealEstatesEvent.NavigateToAddRealEstate
    }


}