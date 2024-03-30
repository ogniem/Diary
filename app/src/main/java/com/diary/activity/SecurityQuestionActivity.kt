package com.diary.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.diary.R
import com.diary.databinding.ActivitySecurityQuestionBinding


class SecurityQuestionActivity : BaseActivity() {
    private val binding by lazy { ActivitySecurityQuestionBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val adapter = ArrayAdapter(
            this,
            R.layout.item_spiner,
            arrayOf(
                "Where were you born?",
                "What is the name of your primary school?",
                "What is your nickname at home?"
            )
        )
        binding.spv.adapter = adapter

        binding.btnSave.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}