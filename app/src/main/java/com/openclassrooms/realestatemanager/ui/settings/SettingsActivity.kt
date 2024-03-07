package com.openclassrooms.realestatemanager.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.SettingsActivityBinding
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity: AppCompatActivity() {

private val viewModel by viewModels<SettingsViewModel>()
    private val binding by viewBinding { SettingsActivityBinding.inflate(it) }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        binding.settingsActivityRadioButtonEuros.setOnClickListener {
            viewModel.setCurrentCurrency(binding.settingsActivityRadioButtonEuros.text.toString())
        }

        binding.settingsActivityRadioButtonDollars.setOnClickListener {
            viewModel.setCurrentCurrency(binding.settingsActivityRadioButtonDollars.text.toString())
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}