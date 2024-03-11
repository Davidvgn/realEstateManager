package com.openclassrooms.realestatemanager.ui.real_estates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.databinding.RealEstateEmptyStateItemBinding
import com.openclassrooms.realestatemanager.databinding.RealEstateItemBinding
import com.openclassrooms.realestatemanager.ui.OnRealEstateClickedListener

class RealEstatesAdapter(
    private val onItemClick: () -> Unit,
    private val listener: OnRealEstateClickedListener,
) : ListAdapter<RealEstatesViewSateItem, RealEstatesAdapter.RealEstatesViewHolder>(
        RealEstatesDiffUtilCallback,
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RealEstatesViewHolder =
        when (RealEstatesViewSateItem.Type.values()[viewType]) {
            RealEstatesViewSateItem.Type.EMPTY_STATE -> {
                RealEstatesViewHolder.EmptyState.create(parent, onItemClick)
            }

            RealEstatesViewSateItem.Type.REAL_ESTATE -> {
                RealEstatesViewHolder.RealEstate.create(parent, listener)
            }
        }

    override fun onBindViewHolder(
        holder: RealEstatesViewHolder,
        position: Int,
    ) {
        when (holder) {
            is RealEstatesViewHolder.EmptyState -> Unit
            is RealEstatesViewHolder.RealEstate -> holder.bind(realEstate = getItem(position) as RealEstatesViewSateItem.RealEstates)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).type.ordinal

    sealed class RealEstatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class EmptyState(
            private val binding: RealEstateEmptyStateItemBinding,
            onItemClick: () -> Unit,
        ) :
            RealEstatesViewHolder(binding.root) {
            init {
                binding.realEstateEmptyStateButton.setOnClickListener {
                    onItemClick.invoke()
                }
            }

            companion object {
                fun create(
                    parent: ViewGroup,
                    onItemClick: () -> Unit,
                ) = EmptyState(
                    binding =
                        RealEstateEmptyStateItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false,
                        ),
                    onItemClick = onItemClick,
                )
            }
        }

        class RealEstate(
            private val binding: RealEstateItemBinding,
            private val listener: OnRealEstateClickedListener,
        ) :
            RealEstatesViewHolder(binding.root) {
            companion object {
                fun create(
                    parent: ViewGroup,
                    listener: OnRealEstateClickedListener,
                ) = RealEstate(
                    binding =
                        RealEstateItemBinding.inflate(
                            LayoutInflater.from(
                                parent.context,
                            ),
                            parent,
                            false,
                        ),
                    listener = listener,
                )
            }

            fun bind(realEstate: RealEstatesViewSateItem.RealEstates) {
                val uri: String? = realEstate.photo

                binding.realEstateItemTextViewType.text = realEstate.realEstatesType
                binding.realEstateItemTextViewCity.text = realEstate.city
                binding.realEstateItemTextViewPrice.text = realEstate.salePrice.toString()
                binding.realEstateItemTextViewCurrency.text = realEstate.currency

                if (binding.realEstateItemTextViewPrice.text == "Préciser le prix") { // todo david gérer hardcoded text
                    binding.realEstateItemTextViewCurrency.visibility = View.GONE
                }

                if (realEstate.status == "Sold") { // todo david gérer hardcoded text
                    binding.relEstateSoldBanner.visibility = View.VISIBLE
                }

                Glide.with(binding.realEstateItemImageView.context)
                    .load(uri)
                    .into(binding.realEstateItemImageView)

                binding.estateItemConstraintLayoutRoot.setOnClickListener {
                    listener.onRealEstateClicked(
                        realEstate.id,
                    )
                }
            }
        }
    }

    object RealEstatesDiffUtilCallback : DiffUtil.ItemCallback<RealEstatesViewSateItem>() {
        override fun areItemsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem,
        ): Boolean =
            when {
                oldItem is RealEstatesViewSateItem.EmptyState && newItem is RealEstatesViewSateItem.EmptyState -> true
                oldItem is RealEstatesViewSateItem.RealEstates && newItem is RealEstatesViewSateItem.RealEstates -> oldItem.id == newItem.id
                else -> false
            }

        override fun areContentsTheSame(
            oldItem: RealEstatesViewSateItem,
            newItem: RealEstatesViewSateItem,
        ): Boolean = oldItem == newItem
    }
}
