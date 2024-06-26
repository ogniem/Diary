package com.diary.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.diary.Common.setPasscode
import com.diary.R
import com.diary.databinding.ActivitySetPassCodeBinding

class PassCodeActivity : BaseActivity() {
    private val binding by lazy { ActivitySetPassCodeBinding.inflate(layoutInflater) }
    private var isConfirm = false
    private var passCodeNow = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBackspace.setOnClickListener {
            editCode(-1)
        }
        binding.btnNumber1.setOnClickListener {
            editCode(1)
        }
        binding.btnNumber2.setOnClickListener {
            editCode(2)
        }
        binding.btnNumber3.setOnClickListener {
            editCode(3)
        }
        binding.btnNumber4.setOnClickListener {
            editCode(4)
        }
        binding.btnNumber5.setOnClickListener {
            editCode(5)
        }
        binding.btnNumber6.setOnClickListener {
            editCode(6)
        }
        binding.btnNumber7.setOnClickListener {
            editCode(7)
        }
        binding.btnNumber8.setOnClickListener {
            editCode(8)
        }
        binding.btnNumber9.setOnClickListener {
            editCode(9)
        }
        binding.btnNumber0.setOnClickListener {
            editCode(0)
        }
        binding.btnContinue.setOnClickListener {
            if (isConfirm && binding.edtPasscode.text.toString().length == 4) {
                setPasscode(binding.edtPasscode.text.toString())
                startActivity(Intent(this, SecurityQuestionActivity::class.java))
            } else {
                binding.tvStatus.text = getText(R.string.finish_confirm_pin)
                if (isConfirm) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.tvStatus.text = getText(R.string.confirm_pin)
                    }, 3000)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.tvStatus.text = getText(R.string.create_4_digit_pin_for_your_diary)
                    }, 3000)
                }
            }
        }
        binding.btnSkip.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (!isConfirm) {
            super.onBackPressed()
        } else {
            binding.tvStatus.text = getText(R.string.create_4_digit_pin_for_your_diary)
            isConfirm = false
            binding.edtPasscode.setText("")
        }
    }

    private fun editCode(key: Int) {
        if (key != -1) {
            if (binding.edtPasscode.text.toString().length == 4) {
                return
            } else {
                val textCurrent = binding.edtPasscode.text.toString()
                binding.edtPasscode.setText(textCurrent + key)
                if ((textCurrent + key).length == 4) {
                    val passCodeConfirm = textCurrent + key
                    if (!isConfirm) {
                        passCodeNow = passCodeConfirm
                        binding.tvStatus.text = getText(R.string.confirm_pin)
                        isConfirm = true
                        binding.edtPasscode.setText("")
                    } else if (passCodeNow == passCodeConfirm) {
                        setPasscode(binding.edtPasscode.text.toString())
                        startActivity(Intent(this, SecurityQuestionActivity::class.java))
                    }else{
                        binding.tvStatus.text = getText(R.string.passcode_fail)
                        binding.edtPasscode.setText("")
                    }
                }
            }
        } else {
            if (binding.edtPasscode.text.toString().length == 0) {
                return
            } else {
                val textCurrent = binding.edtPasscode.text.toString()
                binding.edtPasscode.setText(textCurrent.dropLast(1))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isConfirm = false
        binding.edtPasscode.setText("")
        binding.tvStatus.text = getText(R.string.create_4_digit_pin_for_your_diary)
    }
}