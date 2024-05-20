package com.diary.activity

import android.os.Bundle
import android.widget.Toast
import com.diary.Common.getPasscode
import com.diary.Common.gone
import com.diary.Common.setPasscode
import com.diary.R
import com.diary.databinding.ActivitySetPassCodeBinding

class ChangePasscodeActivity : BaseActivity() {
    private val binding by lazy { ActivitySetPassCodeBinding.inflate(layoutInflater) }
    private var isCorrect = false
    private var isConfirm = false
    private var passConfirm = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvStatus.text = " "
        binding.tvTitle.text = getText(R.string.enter_your_passcode)
        binding.bottom.gone()

        if(intent.getBooleanExtra("FROM_REPASS",false)){
            isCorrect = true
            binding.tvTitle.text = getText(R.string.enter_your_new_passcode)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

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
                    if (!isCorrect) {
                        if (passCodeConfirm == getPasscode()) {
                            isCorrect = true
                            binding.tvTitle.text = getText(R.string.enter_your_new_passcode)
                            binding.edtPasscode.setText("")
                            binding.tvStatus.text = " "
                        } else {
                            binding.tvStatus.text = getText(R.string.error_pass)
                            binding.edtPasscode.setText("")
                        }
                    } else {
                        if (!isConfirm) {
                            isConfirm = true
                            passConfirm = passCodeConfirm
                            binding.tvTitle.text = getText(R.string.confirm_pin)
                            binding.edtPasscode.setText("")
                            binding.tvStatus.text = " "
                        } else {
                            if (passConfirm == passCodeConfirm) {
                                setPasscode(passConfirm)
                                Toast.makeText(
                                    this,
                                    getString(R.string.your_passcode_changde),
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            } else {
                                binding.tvStatus.text = getText(R.string.error_pass)
                                binding.edtPasscode.setText("")
                            }
                        }
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

}