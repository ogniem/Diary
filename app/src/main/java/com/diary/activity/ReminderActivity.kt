package com.diary.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.diary.Common.convertStringToTime
import com.diary.Common.convertTimeToString
import com.diary.Common.getDailyReminder
import com.diary.Common.setDailyReminder
import com.diary.InputFilterMinMax
import com.diary.databinding.ActivityReminderBinding


class ReminderActivity : BaseActivity() {
    private val binding by lazy { ActivityReminderBinding.inflate(layoutInflater) }
    private var isAM = true
    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val timeTriple = convertStringToTime(getDailyReminder())
        timeTriple?.let { (hour, minute, isAM) ->
            this.isAM = isAM
            binding.edtHour.setText(hour)
            binding.edtMinute.setText(minute)
        }

        setSelectAM_PM()

        binding.root.setOnTouchListener { _, _ ->
            hideKeyboard()
            binding.edtHour.clearFocus()
            binding.edtMinute.clearFocus()
            false
        }

        binding.edtMinute.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.edtMinute.text.toString()
                if (text.length == 1) {
                    // Nếu chỉ có một chữ số, thêm số 0 vào trước
                    binding.edtMinute.setText("0$text")
                } else if (text.isBlank()) {
                    binding.edtMinute.setText("00")
                }
            }
        }
        binding.edtHour.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = binding.edtHour.text.toString()
                if (text.length == 1) {
                    // Nếu chỉ có một chữ số, thêm số 0 vào trước
                    binding.edtHour.setText("0$text")
                } else if (text.isBlank()) {
                    binding.edtHour.setText("00")
                }
            }
        }


        binding.edtHour.setFilters(
            arrayOf<InputFilter>(
                InputFilterMinMax("00", "12")
            )
        )
        binding.edtMinute.setFilters(
            arrayOf<InputFilter>(
                InputFilterMinMax("00", "59")
            )
        )

        binding.btnAm.setOnClickListener {
            isAM = true
            setSelectAM_PM()
        }
        binding.btnPm.setOnClickListener {
            isAM = false
            setSelectAM_PM()
        }
        binding.btnContinue.setOnClickListener {
            setDailyReminder(
                convertTimeToString(
                    binding.edtHour.text.toString(),
                    binding.edtMinute.text.toString(),
                    isAM
                )
            )
            startActivity(Intent(this, SetPassCodeActivity::class.java))
        }
        binding.btnSkip.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setSelectAM_PM() {
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


    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.getRootView().windowToken, 0)
    }

}