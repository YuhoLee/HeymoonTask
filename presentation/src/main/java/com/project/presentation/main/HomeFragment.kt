package com.project.presentation.main

import com.project.presentation.R
import com.project.presentation.base.BaseFragment
import com.project.presentation.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding>(
    bindingInflater = FragmentHomeBinding::inflate,
) {
    override fun init() {
        super.init()

    }

    override fun initView() {
        super.initView()

    }

    override fun initListener() {
        super.initListener()

        binding.apply{
            clSearchCollections.setOnClickListener {
                navController.navigate(R.id.action_home_to_search)
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()

    }
}