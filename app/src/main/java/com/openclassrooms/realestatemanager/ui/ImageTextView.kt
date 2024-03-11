package com.openclassrooms.realestatemanager.ui

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.openclassrooms.realestatemanager.R

class ImageTextView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
    ) : FrameLayout(context, attrs) {
        init {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.custom_image_text_view, this, true)
        }

        fun setImageResource(resUri: Uri) {
            findViewById<ImageView>(R.id.custom_imageView_image).setImageURI(resUri)
        }

        fun setText(text: CharSequence) {
            findViewById<TextView>(R.id.custom_imageView_text_title).text = text
        }
    }
