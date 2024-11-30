package com.project.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.domain.model.RemoteCollectionModel
import com.project.presentation.databinding.ListItemCollectionsBinding
import java.util.Locale

class SearchCollectionAdapter: PagingDataAdapter<RemoteCollectionModel, SearchCollectionAdapter.CollectionViewHolder>(
    DiffCallback
) {

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

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RemoteCollectionModel>() {
        override fun areItemsTheSame(
            oldItem: RemoteCollectionModel,
            newItem: RemoteCollectionModel
        ): Boolean = oldItem.productNameKo == newItem.productNameKo

        override fun areContentsTheSame(
            oldItem: RemoteCollectionModel,
            newItem: RemoteCollectionModel
        ): Boolean = oldItem == newItem
    }
}