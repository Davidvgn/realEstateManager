package com.openclassrooms.realestatemanager.ui.real_estates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.RealEstateItemBinding

class RealEstatesAdapter :
    ListAdapter<RealEstatesViewSateItem, RealEstatesAdapter.RealEstatesViewHolder>(EstatesDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstatesViewHolder = RealEstatesViewHolder(
        RealEstateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: RealEstatesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class RealEstatesViewHolder(private val binding: RealEstateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(estate: RealEstatesViewSateItem){
                binding.realEstateItemTextViewType.text = estate.type
                binding.realEstateItemTextViewCity.text = estate.city
            }

    }
    object EstatesDiffUtilCallback : DiffUtil.ItemCallback<RealEstatesViewSateItem>() {
        override fun areItemsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem
        ): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem
        ): Boolean = oldItem == newItem
    }

}