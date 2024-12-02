package com.project.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.domain.model.LocalCollectionModel
import com.project.presentation.databinding.ListItemCollectionsBinding
import java.util.Locale

class FavoritesAdapter(val callback: (LocalCollectionModel) -> Unit) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    private val collectionList: MutableList<LocalCollectionModel> = mutableListOf()

    inner class FavoritesViewHolder(val binding: ListItemCollectionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LocalCollectionModel) {
            binding.apply{
                if(item != null){
                    Glide.with(ivPhoto).load(item.thumbImgUrl).into(ivPhoto)
                    tvTitle.text = if(isKoreanLanguage()){
                        item.productNameKo
                    }else{
                        item.productNameEn
                    }
                    tvMakerAndYear.text = "${item.maker}(${item.manufactureYear})"
                    tvType.text = item.productClass

                    clItem.setOnClickListener {
                        callback.invoke(item)
                    }
                }

            }
        }

        private fun isKoreanLanguage(): Boolean {
            return Locale.getDefault().language == Locale.KOREAN.language
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCollectionsBinding.inflate(inflater, parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int = collectionList.count()

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val item = collectionList[position]
        holder.bind(item)
    }

    fun updateList(list: List<LocalCollectionModel>){
        collectionList.clear()
        collectionList.addAll(list)
        notifyDataSetChanged()
    }
}