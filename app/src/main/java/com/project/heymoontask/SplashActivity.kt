package com.project.heymoontask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity: AppCompatActivity() {
    private var splashJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 2초 대기 후 메인화면으로 이동
        splashJob = lifecycleScope.launch {
            delay(2000) // 2초 대기
            navigateToMain()
        }

    }

    private fun navigateToMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish() // SplashActivity 종료
    }

    override fun onDestroy() {
        super.onDestroy()
        splashJob?.cancel()
    }

}