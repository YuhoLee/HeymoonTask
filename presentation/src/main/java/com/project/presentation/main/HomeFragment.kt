package com.project.presentation.main

import com.project.presentation.base.BaseFragment
import com.project.presentation.databinding.FragmentHomeBinding

class HomeFragment: BaseFragment<FragmentHomeBinding, MainViewModel>(
    bindingInflater = FragmentHomeBinding::inflate,
    viewModelClass = MainViewModel::class.java
) {
    override fun init() {
        super.init()

    }

    override fun initView() {
        super.initView()

    }

    override fun initListener() {
        super.initListener()
    }

    override fun observeViewModel() {
        super.observeViewModel()

    }
}