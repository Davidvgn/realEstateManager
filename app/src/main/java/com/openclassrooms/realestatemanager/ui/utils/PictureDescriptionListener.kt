package com.openclassrooms.realestatemanager.ui.utils

import android.net.Uri

interface PictureDescriptionListener {

    fun onDescriptionFilled(uri : Uri, title: String)
}