package com.openclassrooms.realestatemanager.ui

import android.net.Uri

interface PictureDescriptionListener {

    fun onDescriptionFilled(uri : Uri, title: String)
}