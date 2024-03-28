package com.diary.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diary.Common.getUserName
import com.diary.Common.setUserName
import com.diary.R
import com.diary.databinding.ActivityNameBinding

class NameActivity : BaseActivity() {
    private val binding by lazy { ActivityNameBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.edtUsername.setText(getUserName())

        binding.btnContinue.setOnClickListener {
            setUserName(binding.edtUsername.text.toString())
            startActivity(Intent(this, ReminderActivity::class.java))
        }
    }
}