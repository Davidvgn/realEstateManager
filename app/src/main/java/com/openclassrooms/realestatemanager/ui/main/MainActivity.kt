package com.openclassrooms.realestatemanager.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.ui.NavigationListener
import com.openclassrooms.realestatemanager.ui.add_realEstate.AddRealEstateDialogFragment
import com.openclassrooms.realestatemanager.ui.map.MapFragment
import com.openclassrooms.realestatemanager.ui.realEstates.RealEstatesFragment
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationListener {

    private val binding by viewBinding { MainActivityBinding.inflate(it) }
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setSupportActionBar(binding.mainToolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.commitNow {
                replace(binding.mainFrameLayoutFragmentContainer.id, MapFragment.newInstance())
            }
        }

        binding.mainBottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_map -> displayFragment(MapFragment.newInstance())
                R.id.bottom_nav_list -> displayFragment(RealEstatesFragment.newInstance())
            }
            true
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_FrameLayout_fragment_container, fragment)
            .commitNow()
    }

    override fun displayAddRealEstateDialog() {
        AddRealEstateDialogFragment.newInstance().show(supportFragmentManager, null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.operations_menu, menu)

        val addItem: MenuItem = menu.findItem(R.id.operations_menu_add)
        addItem.setOnMenuItemClickListener {
            AddRealEstateDialogFragment.newInstance().show(supportFragmentManager, null)
            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}
