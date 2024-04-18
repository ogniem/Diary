package com.diary.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diary.Common.setPasscode
import com.diary.R
import com.diary.databinding.ActivitySetPassCodeBinding

class SetPassCodeActivity : BaseActivity() {
    private val binding by lazy { ActivitySetPassCodeBinding.inflate(layoutInflater) }
    private var isConfirm = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBackspace.setOnClickListener {
            editCode(-1)
            Log.d("TAG123", "onCreate: nút xóa")
        }
        binding.btnNumber1.setOnClickListener {
            editCode(1)
            Log.d("TAG123", "onCreate: nút 1")
        }
        binding.btnNumber2.setOnClickListener {
            editCode(2)
            Log.d("TAG123", "onCreate: nút 2")
        }
        binding.btnNumber3.setOnClickListener {
            editCode(3)
            Log.d("TAG123", "onCreate: nút 3")
        }
        binding.btnNumber4.setOnClickListener {
            editCode(4)
            Log.d("TAG123", "onCreate: nút 4")
        }
        binding.btnNumber5.setOnClickListener {
            editCode(5)
            Log.d("TAG123", "onCreate: nút 5")
        }
        binding.btnNumber6.setOnClickListener {
            editCode(6)
            Log.d("TAG123", "onCreate: nút 6")
        }
        binding.btnNumber7.setOnClickListener {
            editCode(7)
            Log.d("TAG123", "onCreate: nút 7")
        }
        binding.btnNumber8.setOnClickListener {
            editCode(8)
            Log.d("TAG123", "onCreate: nút 8")
        }
        binding.btnNumber9.setOnClickListener {
            editCode(9)
            Log.d("TAG123", "onCreate: nút 9")
        }
        binding.btnNumber0.setOnClickListener {
            editCode(0)
            Log.d("TAG123", "onCreate: nút 0")
        }
        binding.btnContinue.setOnClickListener {
            if (isConfirm && binding.edtPasscode.text.toString().length == 4) {
                setPasscode(binding.edtPasscode.text.toString())
                startActivity(Intent(this, SecurityQuestionActivity::class.java))
            }else{
                binding.tvStatus.text
            }
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
                    binding.tvStatus.text = getText(R.string.confirm_pin)
                    isConfirm = true
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