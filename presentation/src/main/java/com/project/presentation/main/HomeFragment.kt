package com.project.presentation.main

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.project.presentation.R
import com.project.presentation.base.BaseFragment
import com.project.presentation.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    bindingInflater = FragmentHomeBinding::inflate,
) {
    private var backPressJob: Job? = null

    override fun initListener() {
        super.initListener()

        binding.apply {
            clSearchCollections.setOnClickListener {
                navController.navigate(R.id.action_home_to_search)
            }
        }

        // 뒤로가기 콜백 등록
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressJob?.isActive == true) {
                        // 두 번째 뒤로가기: 앱 종료
                        requireActivity().finish()
                    } else {
                        // 첫 번째 뒤로가기: 코루틴 실행
                        backPressJob = lifecycleScope.launch {
                            Toast.makeText(
                                requireContext(),
                                requireContext().getString(R.string.toast_double_back_text),
                                Toast.LENGTH_SHORT
                            ).show()
                            delay(2000) // 2초 대기
                            backPressJob = null // 상태 초기화
                        }
                    }
                }
            })
    }

}