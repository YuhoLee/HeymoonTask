package com.project.presentation.search.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.project.presentation.databinding.DialogErrorPopupBinding

class DialogErrorPopup(
    context: Context,
): Dialog(context) {
    private var _binding: DialogErrorPopupBinding? = null
    private val binding get() = _binding!!

    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DialogErrorPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initView()
        initListener()
    }


    private fun init(){
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        setCancelable(false)
    }

    private fun initView(){
        binding.apply{
            tvTitle.text = title
        }
    }

    private fun initListener(){
        binding.tvConfirm.setOnClickListener {
            dismiss()
        }
    }

    fun setTitle(text: String){
        title = text
    }
}