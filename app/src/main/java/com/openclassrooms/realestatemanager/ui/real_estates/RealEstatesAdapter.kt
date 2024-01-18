package com.openclassrooms.realestatemanager.ui.real_estates

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.RealEstateEmptyStateItemBinding
import com.openclassrooms.realestatemanager.databinding.RealEstateItemBinding

class RealEstatesAdapter : ListAdapter<RealEstatesViewSateItem, RealEstatesAdapter.RealEstatesViewHolder>(RealEstatesDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstatesViewHolder =
        when (RealEstatesViewSateItem.Type.values()[viewType]) {
            RealEstatesViewSateItem.Type.EMPTY_STATE -> {
                Log.d("DavidVGN", "Creating EmptyState ViewHolder")
                RealEstatesViewHolder.EmptyState.create(parent)
            }
            RealEstatesViewSateItem.Type.REAL_ESTATE ->{
                Log.d("DavidVGN", "Creating REAL_ESTATE ViewHolder")
                RealEstatesViewHolder.RealEstate.create(parent)}
        }

    override fun onBindViewHolder(holder: RealEstatesViewHolder, position: Int) {
        when (holder) {
            is RealEstatesViewHolder.EmptyState -> Unit
            is RealEstatesViewHolder.RealEstate -> holder.bind(realEstate = getItem(position) as RealEstatesViewSateItem.RealEstates)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    sealed class RealEstatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class EmptyState(private val binding: RealEstateEmptyStateItemBinding) :
            RealEstatesViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = EmptyState(
                    binding = RealEstateEmptyStateItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }

        class RealEstate(private val binding: RealEstateItemBinding) :
            RealEstatesViewHolder(binding.root) {
            companion object {
                fun create(parent: ViewGroup) = RealEstate(
                    binding = RealEstateItemBinding.inflate(
                        LayoutInflater.from(
                            parent.context), parent, false
                    )
                )
            }
            fun bind(realEstate: RealEstatesViewSateItem.RealEstates) {
                val resourceId: Int = R.drawable.sample_image
//                val uri: Uri =
//                    Uri.parse("android.resource://com.openclassrooms.realestatemanager/$resourceId")
                binding.realEstateItemTextViewType.text = realEstate.realEstatesType
                binding.realEstateItemTextViewCity.text = realEstate.city
                binding.realEstateItemTextViewPrice.text = realEstate.salePrice.toString()

//                Glide.with(binding.realEstateItemImageView.context)
//                    .load(uri)
//                    .into(binding.realEstateItemImageView)
            }
        }

    }

    object RealEstatesDiffUtilCallback : DiffUtil.ItemCallback<RealEstatesViewSateItem>() {
        override fun areItemsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem
        ): Boolean = when {
            oldItem is RealEstatesViewSateItem.EmptyState && newItem is RealEstatesViewSateItem.EmptyState -> true
            oldItem is RealEstatesViewSateItem.RealEstates && newItem is RealEstatesViewSateItem.RealEstates -> oldItem.id == newItem.id
            else -> false
        }


        override fun areContentsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem
        ): Boolean = oldItem == newItem
    }

}