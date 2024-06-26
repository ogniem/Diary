package com.diary.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.diary.Common.getSercurityAns
import com.diary.Common.getSercurityQues
import com.diary.Common.setSercurityAns
import com.diary.Common.setSercurityQues
import com.diary.R
import com.diary.databinding.ActivitySecurityQuestionBinding


class SecurityQuestionActivity : BaseActivity() {
    private val binding by lazy { ActivitySecurityQuestionBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.spv.setSelection(getSercurityQues())

        binding.edtAnswer.setText(getSercurityAns())

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

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            if (binding.edtAnswer.text.isNotBlank()) {
                setSercurityQues(binding.spv.selectedItemPosition)
                setSercurityAns(binding.edtAnswer.text.toString())
                if(intent.getBooleanExtra("FROM_SETTING",false)){
                    Toast.makeText(this, getText(R.string.change_sercurity), Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }else{
                Toast.makeText(this, getText(R.string.change_sercurity_error), Toast.LENGTH_LONG).show()
            }
        }
    }
}