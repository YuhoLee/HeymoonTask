package com.project.presentation.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.presentation.R
import com.project.presentation.databinding.LayoutBaseActionHeaderBinding

class BaseActionHeader: ConstraintLayout {
    private lateinit var binding: LayoutBaseActionHeaderBinding

    constructor(context: Context): super(context){
        initView()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        initView()
        getAttrs(attrs)
    }

    private fun initView(){
        binding = LayoutBaseActionHeaderBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun getAttrs(attrs: AttributeSet){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.header)
        setTypedArray(typedArray)
        typedArray.recycle()
    }

    private fun setTypedArray(typedArray: TypedArray){
        val titleStr = typedArray.getString(R.styleable.header_headerTitle)
        val backEnabled = typedArray.getBoolean(R.styleable.header_backEnabled, false)
        val favoriteEnabled = typedArray.getBoolean(R.styleable.header_favoriteEnabled, false)

        binding.apply{
            tvActionHeaderTitle.text = titleStr ?: ""
            ivBack.visibility = if(backEnabled) View.VISIBLE else View.INVISIBLE
            ivFavorite.visibility = if(favoriteEnabled) View.VISIBLE else View.INVISIBLE
        }

    }

    fun setTitle(title: String){
        binding.tvActionHeaderTitle.text = title
    }

    fun setOnBackPressed(callback: () -> Unit){
        binding.ivBack.setOnClickListener {
            callback.invoke()
        }
    }

    fun setOnFavoritePressed(callback: () -> Unit){
        binding.ivFavorite.setOnClickListener {
            callback.invoke()
        }
    }
}