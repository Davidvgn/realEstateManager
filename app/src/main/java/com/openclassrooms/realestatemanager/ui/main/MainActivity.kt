package com.openclassrooms.realestatemanager.ui.main

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FilterRealEstateBinding
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.ui.OnRealEstateClickedListener
import com.openclassrooms.realestatemanager.ui.add_real_estate.AddRealEstateActivity
import com.openclassrooms.realestatemanager.ui.details.DetailsFragment
import com.openclassrooms.realestatemanager.ui.map.MapFragment
import com.openclassrooms.realestatemanager.ui.real_estates_home.RealEstateHomeFragment
import com.openclassrooms.realestatemanager.ui.settings.SettingsActivity
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRealEstateClickedListener {

    private val binding by viewBinding { MainActivityBinding.inflate(it) }
    private val viewModel by viewModels<MainViewModel>()


    companion object {
        private const val KEY_REAL_ESTATE_ID = "KEY_REAL_ESTATE_ID"
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            viewModel.onPermissionUpdated()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setSupportActionBar(binding.mainToolbar)

        if (savedInstanceState == null) {
            displayFragment(RealEstateHomeFragment.newInstance())
        }

        //Resolves icon and title unwanted presence in filter view
        val menu = binding.mainNavigationView.menu
        val menuItem = menu.findItem(R.id.filter_menu)
        menuItem.icon = null
        menuItem.title = null


        binding.mainBottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_list -> {
                    displayFragment(RealEstateHomeFragment.newInstance())
                    binding.mainToolbar.setTitle(R.string.RealEstate)
                }

                R.id.bottom_nav_map -> {
                    displayFragment(MapFragment.newInstance())
                    binding.mainToolbar.setTitle(R.string.Map)

                }
            }
            true
        }

        val filterRealEstateBinding: FilterRealEstateBinding =
            FilterRealEstateBinding.bind(binding.mainNavigationView.getHeaderView(0))

        filterRealEstateBinding.filterButton.setOnClickListener {
            binding.mainDrawerLayout.close()

        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameLayoutFragmentContainer.id, fragment)
            .commitNow()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.operations_menu, menu)
        menuInflater.inflate(R.menu.filter_menu, menu)

        val addItem: MenuItem = menu.findItem(R.id.operations_menu_add)
        addItem.setOnMenuItemClickListener {
            val intent = Intent(this, AddRealEstateActivity::class.java)
            startActivity(intent)
            true
        }

        val filterItem: MenuItem = menu.findItem(R.id.filter_menu)
        filterItem.setOnMenuItemClickListener {
            binding.mainDrawerLayout.open()
            true
        }

        val settingsItem: MenuItem = menu.findItem(R.id.settings)
        settingsItem.setOnMenuItemClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }

        return super.onCreateOptionsMenu(menu)
    }


    override fun onResume() {
        super.onResume()
        viewModel.onPermissionUpdated()
    }

    override fun onRealEstateClicked(realEstateId: Long) {
        val fragment = DetailsFragment.newInstance()
        val args = Bundle().apply {
            putLong(KEY_REAL_ESTATE_ID, realEstateId)
        }
        fragment.arguments = args

        if (!resources.getBoolean(R.bool.isTablet) &&
            resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        ) {
            supportFragmentManager.beginTransaction()
                .replace(binding.mainFrameLayoutFragmentContainer.id, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}