package com.diary.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.diary.Common.getSercurityAns
import com.diary.Common.getSercurityQues
import com.diary.R
import com.diary.databinding.ActivitySecurityQuestionBinding

class FogortPasscodeActivity : BaseActivity() {
    private val binding by lazy { ActivitySecurityQuestionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvTitle.text = getText(R.string.get_passcode_again)

        binding.btnSave.text = getText(R.string.txt_continue)

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
            if(binding.spv.selectedItemPosition == getSercurityQues()&& binding.edtAnswer.text.toString().toLowerCase() == getSercurityAns().toLowerCase()){
                startActivity(Intent(this, ChangePasscodeActivity::class.java).putExtra("FROM_REPASS",true))
                finish()
            }else{
                Toast.makeText(
                    this,
                    getString(R.string.error_ques),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}