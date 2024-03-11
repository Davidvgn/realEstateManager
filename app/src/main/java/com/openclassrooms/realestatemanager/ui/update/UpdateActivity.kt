package com.openclassrooms.realestatemanager.ui.update

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateActivity : AppCompatActivity() {
    companion object {
        const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_real_estate_activity)

        val realEstateId = intent.getLongExtra(KEY_REAL_ESTATE_ID, -1)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.update_real_estate_FrameLayout_fragment_container,
                    UpdateRealEstateFragment.newInstance(realEstateId),
                )
                .commitNow()
        }
    }
}
