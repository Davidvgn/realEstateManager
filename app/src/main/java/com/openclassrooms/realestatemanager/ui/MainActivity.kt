package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.ui.realEstates.RealEstatesFragment
import com.openclassrooms.realestatemanager.ui.utils.viewBinding

class MainActivity: AppCompatActivity() {

    private val binding by viewBinding { MainActivityBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(binding.mainFrameLayoutFragmentContainer.id, RealEstatesFragment.newInstance())
            }
        }
    }
}