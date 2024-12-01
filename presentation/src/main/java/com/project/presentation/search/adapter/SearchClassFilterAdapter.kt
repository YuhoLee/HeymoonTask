package com.project.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.presentation.databinding.ListItemSearchClassFilterBinding
import com.project.presentation.search.ProductClass

class SearchClassFilterAdapter : RecyclerView.Adapter<SearchClassFilterAdapter.ClassViewHolder>() {
    private val selectedItemList = mutableListOf<ProductClass>()

    inner class ClassViewHolder(private val binding: ListItemSearchClassFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item = ProductClass.entries.toList()[position]
            val isSelected = selectedItemList.contains(item)
            binding.apply {
                tvName.text = root.context.getString(item.strId)
                tvName.isSelected = isSelected
                ivCheck.isSelected = isSelected
                clItem.setOnClickListener {
                    changeSelectState(position, isSelected)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSearchClassFilterBinding.inflate(inflater, parent, false)
        return ClassViewHolder(binding)
    }

    override fun getItemCount(): Int = ProductClass.entries.toList().size

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(position)
    }

    fun getSelectedItemList() = selectedItemList

    fun updateList(list: List<ProductClass>) {
        selectedItemList.clear()
        selectedItemList.addAll(list)
        notifyDataSetChanged()
    }

    private fun changeSelectState(position: Int, isSelected: Boolean) {
        val item = ProductClass.entries.toList()[position]
        if (isSelected) {
            selectedItemList.removeAt(selectedItemList.indexOf(item))
        } else {
            selectedItemList.add(item)
        }
        notifyItemChanged(position)
    }
}