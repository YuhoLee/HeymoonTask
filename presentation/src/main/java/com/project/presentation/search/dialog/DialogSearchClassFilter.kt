package com.project.presentation.search.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.presentation.databinding.DialogSearchClassFilterBinding
import com.project.presentation.search.SearchViewModel
import com.project.presentation.search.adapter.SearchClassFilterAdapter

class DialogSearchClassFilter : BottomSheetDialogFragment() {
    private var _binding: DialogSearchClassFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var searchClassFilterAdapter: SearchClassFilterAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState).apply {
            if (this is BottomSheetDialog) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                it.isNestedScrollingEnabled = false
                val bottomSheetBehavior = BottomSheetBehavior.from(it)
                bottomSheetBehavior.isDraggable = false
            }
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSearchClassFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    private fun initView() {
        searchClassFilterAdapter = SearchClassFilterAdapter()
        searchClassFilterAdapter.updateList(viewModel.uiState.value.productClasses)
        binding.rvClass.adapter = searchClassFilterAdapter
    }

    private fun initListener() {
        binding.tvConfirm.setOnClickListener {
            viewModel.updateProductClassesFilter(searchClassFilterAdapter.getSelectedItemList())
            dialog?.dismiss()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}