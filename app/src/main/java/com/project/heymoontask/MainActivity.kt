package com.project.heymoontask

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.project.heymoontask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.HomeFragment, R.id.FavoritesFragment -> {
                    // 홈 및 즐겨찾기 화면에서는 바텀 네비게이션 표시
                    binding.bottomNav.visibility = View.VISIBLE
                }

                else -> {
                    // 그 외 화면에서는 바텀 네비게이션 숨김
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }
    }
}