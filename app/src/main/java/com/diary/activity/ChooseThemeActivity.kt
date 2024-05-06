package com.diary.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import com.diary.Common.getThemeHome
import com.diary.Common.setThemeHome
import com.diary.Common.visible
import com.diary.databinding.ActivityChooseThemeBinding

class ChooseThemeActivity : BaseActivity() {
    private val binding by lazy { ActivityChooseThemeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        when (getThemeHome()) {
            1 -> {
                binding.btn1.imageTintList = ColorStateList.valueOf(Color.parseColor("#33000000"))
                binding.icTick1.visible()
            }

            2 -> {
                binding.btn2.imageTintList = ColorStateList.valueOf(Color.parseColor("#33000000"))
                binding.icTick2.visible()
            }

            3 -> {
                binding.btn3.imageTintList = ColorStateList.valueOf(Color.parseColor("#33000000"))
                binding.icTick3.visible()
            }

            4 -> {
                binding.btn4.imageTintList = ColorStateList.valueOf(Color.parseColor("#33000000"))
                binding.icTick4.visible()
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btn1.setOnClickListener {
            setThemeHome(1)
            finish()
        }
        binding.btn2.setOnClickListener {
            setThemeHome(2)
            finish()
        }
        binding.btn3.setOnClickListener {
            setThemeHome(3)
            finish()
        }
        binding.btn4.setOnClickListener {
            setThemeHome(4)
            finish()
        }
    }
}