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
                getText(R.string.question_1),
                getText(R.string.question_2),
                getText(R.string.question_3),
                getText(R.string.question_4),
                getText(R.string.question_5),
            )
        )

        binding.spv.adapter = adapter

        binding.btnSave.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}