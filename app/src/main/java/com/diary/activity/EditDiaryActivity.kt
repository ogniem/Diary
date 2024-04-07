package com.diary.activity

import android.os.Bundle
import com.diary.databinding.ActivityCreateDiaryBinding

class EditDiaryActivity: BaseActivity() {
    private val binding by lazy { ActivityCreateDiaryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}