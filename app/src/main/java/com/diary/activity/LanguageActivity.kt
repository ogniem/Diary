package com.diary.activity

import android.content.Intent
import android.os.Bundle
import com.diary.Common
import com.diary.Common.isFirstOpenApp
import com.diary.adapter.LanguageAdapter
import com.diary.databinding.ActivityLanguageBinding

class LanguageActivity : BaseActivity() {
    private val binding by lazy { ActivityLanguageBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = LanguageAdapter(this)
        binding.rcvLang.adapter = adapter
        binding.btnDone.setOnClickListener {
            Common.saveLanguage(this, adapter.getSelected())
            startActivity(Intent(this, NameActivity::class.java))
            finish()

        }
    }

}