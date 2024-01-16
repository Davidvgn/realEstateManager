package com.openclassrooms.realestatemanager.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.MainActivityBinding
import com.openclassrooms.realestatemanager.ui.add_real_estate.AddRealEstateActivity
import com.openclassrooms.realestatemanager.ui.map.MapFragment
import com.openclassrooms.realestatemanager.ui.real_estates.RealEstatesFragment
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding { MainActivityBinding.inflate(it) }
    private val viewModel by viewModels<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setSupportActionBar(binding.mainToolbar)

        if (savedInstanceState == null) {
            displayFragment(RealEstatesFragment.newInstance())
        }

        binding.mainBottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_list -> displayFragment(RealEstatesFragment.newInstance())
                R.id.bottom_nav_map -> displayFragment(MapFragment.newInstance())
            }
            true
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameLayoutFragmentContainer.id, fragment)
            .commitNow()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.operations_menu, menu)
        menuInflater.inflate(R.menu.navigation_menu, menu)

        val addItem: MenuItem = menu.findItem(R.id.operations_menu_add)
        addItem.setOnMenuItemClickListener {
            val intent = Intent(this, AddRealEstateActivity::class.java)
            startActivity(intent)
            true
        }

        return super.onCreateOptionsMenu(menu)
    }
}