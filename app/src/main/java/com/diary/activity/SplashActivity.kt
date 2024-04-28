package com.diary.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.diary.Common.getPasscode
import com.diary.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            if (getPasscode().isNotBlank()) {
                showDialog {
                    startActivity(Intent(this, IntroActivity::class.java))
                    finish()
                }
            } else {
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }
        }
    }
}