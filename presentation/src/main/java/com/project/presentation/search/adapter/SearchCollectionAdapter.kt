package com.project.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.domain.model.RemoteCollectionModel
import com.project.presentation.databinding.ListItemCollectionsBinding
import java.util.Locale

class SearchCollectionAdapter(val onItemClick: (RemoteCollectionModel) -> Unit): RecyclerView.Adapter<SearchCollectionAdapter.CollectionViewHolder>() {
    private val collectionList: MutableList<RemoteCollectionModel> = mutableListOf()

    inner class CollectionViewHolder(
        private val binding: ListItemCollectionsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RemoteCollectionModel?) {
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
                        onItemClick.invoke(item)
                    }
                }

            }
        }

        private fun isKoreanLanguage(): Boolean {
            return Locale.getDefault().language == Locale.KOREAN.language
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCollectionsBinding.inflate(inflater, parent, false)
        return CollectionViewHolder(binding)
    }

    override fun getItemCount(): Int = collectionList.count()

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = collectionList[position]
        holder.bind(item)
    }

    fun updateList(list: List<RemoteCollectionModel>){
        collectionList.clear()
        collectionList.addAll(list)
    }

}