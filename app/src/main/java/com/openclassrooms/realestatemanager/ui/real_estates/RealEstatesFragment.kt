package com.openclassrooms.realestatemanager.ui.real_estates

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstatesFragmentBinding
import com.openclassrooms.realestatemanager.ui.OnRealEstateClickedListener
import com.openclassrooms.realestatemanager.ui.add_real_estate.AddRealEstateActivity
import com.openclassrooms.realestatemanager.ui.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstatesFragment : Fragment(R.layout.real_estates_fragment) {

    companion object {
        fun newInstance() = RealEstatesFragment()
    }

    private val binding by viewBinding { RealEstatesFragmentBinding.bind(it) }
    private val viewModel by viewModels<RealEstatesViewModel>()
    private lateinit var onRealEstateClickedListener: OnRealEstateClickedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRealEstateClickedListener) {
            onRealEstateClickedListener = context
        } else {
            throw RuntimeException("$context must implement OnRealEstateClickedListener")
        }    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RealEstatesAdapter({
            navigateToAddRealEstateActivity()
        }, onRealEstateClickedListener)


        binding.realEstateRecyclerView.adapter = adapter
        val resourceId: Int = R.drawable.sample_image

        viewModel.viewStateLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }



    private fun navigateToAddRealEstateActivity() {
        val intent = Intent(activity, AddRealEstateActivity::class.java)
        startActivity(intent)
    }
}