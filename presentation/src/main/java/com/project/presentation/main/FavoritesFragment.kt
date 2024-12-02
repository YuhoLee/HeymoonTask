package com.project.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.presentation.R
import com.project.presentation.base.BaseFragment
import com.project.presentation.databinding.FragmentFavoritesBinding
import com.project.presentation.detail.item.CollectionDetailItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(
    bindingInflater = FragmentFavoritesBinding::inflate,
) {
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter
    private var backPressJob: Job? = null

    override fun init() {
        super.init()

        favoritesAdapter = FavoritesAdapter {
            val collectionDetailItem = CollectionDetailItem(
                productNameKo = it.productNameKo,
                productNameEn = it.productNameEn,
                maker = it.maker,
                productClass = it.productClass,
                whSize = it.whSize,
                manufactureYear = it.manufactureYear,
                collectYear = it.collectYear,
                materialTechnic = it.materialTechnic,
                thumbImgUrl = it.thumbImgUrl,
                mainImgUrl = it.mainImgUrl,
            )

            // 수집품 아이템 누를 떄에 대한 수행
            navController.navigate(
                resId = R.id.action_favorites_to_collection_detail,
                args = Bundle().apply {
                    putString("prevRoute", "Favorites")
                    putParcelable("item", collectionDetailItem)
                }
            )
        }
    }

    override fun initView() {
        super.initView()

        binding.rvFavorites.adapter = favoritesAdapter
    }

    override fun initListener() {
        super.initListener()


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

    override fun observeViewModel() {
        super.observeViewModel()

        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                uiState.collectLatest {
                    withContext(Dispatchers.Main) {
                        favoritesAdapter.updateList(it.favorites)
                    }
                }
            }
        }
    }

}