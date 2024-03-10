package com.openclassrooms.realestatemanager.ui.add_form

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ListAdapter
import com.openclassrooms.realestatemanager.databinding.AddAgentSpinnerItemBinding
import com.openclassrooms.realestatemanager.domain.agent.model.AgentEntity

class AgentSpinnerAdapter() : ListAdapter, Filterable {
    private val dataSetObservable = DataSetObservable()
    private var items = emptyList<AgentEntity>()

    override fun registerDataSetObserver(observer: DataSetObserver) {
        dataSetObservable.registerObserver(observer)
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver) {
        dataSetObservable.unregisterObserver(observer)
    }

    override fun areAllItemsEnabled(): Boolean = true

    override fun isEnabled(position: Int): Boolean = true

    override fun getItemViewType(position: Int): Int = 0

    override fun getViewTypeCount(): Int = 1

    override fun isEmpty(): Boolean = count == 0

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): AgentEntity? = items.getOrNull(position)

    override fun getItemId(position: Int): Long = getItem(position)?.id ?: -1

    override fun hasStableIds(): Boolean = true

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
    ): View {
        val binding =
            if (convertView != null) {
                AddAgentSpinnerItemBinding.bind(convertView)
            } else {
                AddAgentSpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }
        getItem(position)?.let {
            binding.addAgentItemTextViewName.text = it.name
        }
        return binding.root
    }

    override fun getFilter() =
        object : Filter() {
            override fun performFiltering(constraint: CharSequence) = FilterResults()

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults,
            ) {}

            override fun convertResultToString(resultValue: Any): CharSequence {
                return (resultValue as AgentEntity).name
            }
        }

    fun setData(items: List<AgentEntity>) {
        this.items = items
        dataSetObservable.notifyChanged()
    }
}
