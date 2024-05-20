package com.diary.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diary.Common.isEnableLock
import com.diary.Common.isEnableReminder
import com.diary.Common.setEnableLock
import com.diary.Common.setEnableReminder
import com.diary.R
import com.diary.databinding.ActivitySetDiaryLockBinding

class SetDiaryLockActivity : BaseActivity() {
    private val binding by lazy { ActivitySetDiaryLockBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.sw.isChecked = isEnableLock()
        binding.sw.setOnCheckedChangeListener { _, isChecked ->
            setEnableLock(isChecked)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnChangepass.setOnClickListener {
            startActivity(Intent(this, ChangePasscodeActivity::class.java))
        }

        binding.btnSetques.setOnClickListener {
            startActivity(Intent(this, SecurityQuestionActivity::class.java).putExtra("FROM_SETTING", true))
        }
    }
}