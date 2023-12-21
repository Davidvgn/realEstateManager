package com.openclassrooms.realestatemanager.ui.add_real_estate

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.AddRealEstateActivityBinding
import com.openclassrooms.realestatemanager.ui.addform.AddFormFragment
import com.openclassrooms.realestatemanager.ui.addform.AddFormConfirmationDialog
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRealEstateActivity : AppCompatActivity() {

    private val binding by viewBinding { AddRealEstateActivityBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.addRealEstateToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(binding.addRealEstateFrameLayoutFragmentContainer.id,
                    AddFormFragment.newInstance()
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_format_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_form_save_button) {

        } else {
            AddFormConfirmationDialog.newInstance().show(supportFragmentManager, null)
//Todo David if yes :
        // return super.onOptionsItemSelected(item)

        }
        return true

    }
}