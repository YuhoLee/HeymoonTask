package com.project.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.presentation.databinding.ListItemSearchOrderByBinding
import com.project.presentation.search.SortType

class SearchOrderByAdapter(val callback: (SortType) -> Unit) : RecyclerView.Adapter<SearchOrderByAdapter.ClassViewHolder>() {
    private var selectedItem: SortType = SortType.ManufactureYearAsc

    inner class ClassViewHolder(private val binding: ListItemSearchOrderByBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = SortType.entries.toList()[position]
            val isSelected = item == selectedItem
            binding.apply {
                tvName.text = root.context.getString(item.strId)
                tvName.isSelected = isSelected

                clItem.setOnClickListener {
                    callback.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSearchOrderByBinding.inflate(inflater, parent, false)
        return ClassViewHolder(binding)
    }

    override fun getItemCount(): Int = SortType.entries.toList().size

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setSelectedItem(item: SortType){
        selectedItem = item
    }
}