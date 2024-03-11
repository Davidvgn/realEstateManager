package com.openclassrooms.realestatemanager.ui

import android.net.Uri

interface OnPictureClickedListener {
    fun onPictureClickedListener(
        uri: Uri,
        title: String,
    )
}
