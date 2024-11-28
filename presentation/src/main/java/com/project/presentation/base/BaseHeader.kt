package com.project.presentation.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.presentation.R
import com.project.presentation.databinding.LayoutBaseHeaderBinding

class BaseHeader: ConstraintLayout {
    private lateinit var binding: LayoutBaseHeaderBinding

    constructor(context: Context): super(context){
        initView()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        initView()
        getAttrs(attrs)
    }

    private fun initView(){
        binding = LayoutBaseHeaderBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private fun getAttrs(attrs: AttributeSet){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.header)
        setTypedArray(typedArray)
        typedArray.recycle()
    }

    private fun setTypedArray(typedArray: TypedArray){
        val titleStr = typedArray.getString(R.styleable.header_headerTitle)
        if(titleStr != null){
            binding.tvHeaderTitle.text = titleStr
        }
    }
}