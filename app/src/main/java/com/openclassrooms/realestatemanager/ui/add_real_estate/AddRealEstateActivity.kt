package com.openclassrooms.realestatemanager.ui.add_real_estate

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.openclassrooms.realestatemanager.databinding.AddRealEstateActivityBinding
import com.openclassrooms.realestatemanager.ui.OnPictureClickedListener
import com.openclassrooms.realestatemanager.ui.add_form.AddFormConfirmationDialog
import com.openclassrooms.realestatemanager.ui.add_form.AddFormFragment
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateActivity : AppCompatActivity(), OnPictureClickedListener {
    private val binding by viewBinding { AddRealEstateActivityBinding.inflate(it) }
    private val viewModel by viewModels<AddRealEstateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.addRealEstateToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(
                    binding.addRealEstateFrameLayoutFragmentContainer.id,
                    AddFormFragment.newInstance(),
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AddFormConfirmationDialog.newInstance().show(supportFragmentManager, null)
        return true
    }

    override fun onPictureClickedListener(
        uri: Uri,
        title: String,
    ) {
        viewModel.deleteTemporaryPicture(uri, title)
    }
}
