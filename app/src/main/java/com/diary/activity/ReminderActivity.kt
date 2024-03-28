package com.diary.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diary.R
import com.diary.databinding.ActivityReminderBinding

class ReminderActivity : BaseActivity() {
    private val binding by lazy { ActivityReminderBinding.inflate(layoutInflater) }
    var isAM = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setselectAM_PM()

        binding.btnAm.setOnClickListener {
            isAM = true
            setselectAM_PM()
        }
        binding.btnPm.setOnClickListener {
            isAM = false
            setselectAM_PM()
        }
        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, SetPassCodeActivity::class.java))
        }
    }

    private fun setselectAM_PM() {
        if (isAM) {
            binding.btnAm.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#766DE6"))
            binding.btnPm.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
            binding.btnAm.setTextColor(Color.WHITE)
            binding.btnPm.setTextColor(Color.parseColor("#383655"))
        } else {
            binding.btnPm.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#766DE6"))
            binding.btnAm.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
            binding.btnPm.setTextColor(Color.WHITE)
            binding.btnAm.setTextColor(Color.parseColor("#383655"))
        }
    }
}