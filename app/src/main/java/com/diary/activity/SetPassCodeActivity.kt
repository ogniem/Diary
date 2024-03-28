package com.diary.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diary.R
import com.diary.databinding.ActivitySetPassCodeBinding

class SetPassCodeActivity : BaseActivity() {
    private val binding by lazy { ActivitySetPassCodeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBackspace.setOnClickListener {

            binding.edtPasscode.text
            Log.d("TAG123", "onCreate: nút xóa")
        }
        binding.btnNumber1.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 1")
        }
        binding.btnNumber2.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 2")
        }
        binding.btnNumber3.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 3")
        }
        binding.btnNumber4.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 4")
        }
        binding.btnNumber5.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 5")
        }
        binding.btnNumber6.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 6")
        }
        binding.btnNumber7.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 7")
        }
        binding.btnNumber8.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 8")
        }
        binding.btnNumber9.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 9")
        }
        binding.btnNumber0.setOnClickListener {
            Log.d("TAG123", "onCreate: nút 0")
        }
        binding.btnContinue.setOnClickListener {

        }
    }
}