package com.diary.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import com.diary.Common
import com.diary.Common.getDailyReminder
import com.diary.Common.getRepeat
import com.diary.Common.isEnableReminder
import com.diary.Common.setDailyReminder
import com.diary.Common.setEnableReminder
import com.diary.Common.setReminder
import com.diary.Common.setRepeat
import com.diary.InputFilterMinMax
import com.diary.R
import com.diary.databinding.ActivityDailyReminderSettingBinding
import com.diary.databinding.DialogSettimeBinding
import com.diary.databinding.PopupRepeatBinding

class DailyReminderSettingActivity : BaseActivity() {
    private val binding by lazy { ActivityDailyReminderSettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.sw.isChecked = isEnableReminder()

        binding.tvRepeat.text = when(getRepeat()){
            1->{getText(R.string.do_not_repeat)}
            2->{getText(R.string.every_day)}
            3->{getText(R.string.weekly)}
            4->{getText(R.string.monthly)}
            5->{getText(R.string.yearly)}
            else -> {getText(R.string.every_day)}
        }

        binding.sw.setOnCheckedChangeListener { _, isChecked ->
            setEnableReminder(isChecked)
        }
        binding.tvTime.text = getDailyReminder()

        binding.btnSettime.setOnClickListener {
            showDialogSetTime()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnRepeat1.setOnClickListener {
            showPopupRepeat()
        }
    }

    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    private fun showDialogSetTime() {
        val bindingDialog = DialogSettimeBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(bindingDialog.root)
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        var isAMSelect = true

        val timeTriple = Common.convertStringToHour(getDailyReminder())
        timeTriple?.let { (hour, minute, isAM) ->
            isAMSelect = isAM
            if (isAMSelect) {
                bindingDialog.btnAm.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#766DE6"))
                bindingDialog.btnPm.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
                bindingDialog.btnAm.setTextColor(Color.WHITE)
                bindingDialog.btnPm.setTextColor(Color.parseColor("#383655"))
            } else {
                bindingDialog.btnPm.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#766DE6"))
                bindingDialog.btnAm.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
                bindingDialog.btnPm.setTextColor(Color.WHITE)
                bindingDialog.btnAm.setTextColor(Color.parseColor("#383655"))
            }
            bindingDialog.edtHour.setText(hour)
            bindingDialog.edtMinute.setText(minute)
        }
        bindingDialog.btnAm.setOnClickListener {
            isAMSelect = true
            bindingDialog.btnAm.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#766DE6"))
            bindingDialog.btnPm.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
            bindingDialog.btnAm.setTextColor(Color.WHITE)
            bindingDialog.btnPm.setTextColor(Color.parseColor("#383655"))
        }
        bindingDialog.btnPm.setOnClickListener {
            isAMSelect = false
            bindingDialog.btnPm.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#766DE6"))
            bindingDialog.btnAm.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
            bindingDialog.btnPm.setTextColor(Color.WHITE)
            bindingDialog.btnAm.setTextColor(Color.parseColor("#383655"))
        }
        bindingDialog.edtHour.setFilters(
            arrayOf<InputFilter>(
                InputFilterMinMax("00", "12")
            )
        )
        bindingDialog.edtMinute.setFilters(
            arrayOf<InputFilter>(
                InputFilterMinMax("00", "59")
            )
        )

        bindingDialog.containter.setOnTouchListener { _, _ ->
            hideKeyboard()
            bindingDialog.edtHour.clearFocus()
            bindingDialog.edtMinute.clearFocus()
            false
        }

        bindingDialog.edtMinute.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = bindingDialog.edtMinute.text.toString()
                if (text.length == 1) {
                    // Nếu chỉ có một chữ số, thêm số 0 vào trước
                    bindingDialog.edtMinute.setText("0$text")
                } else if (text.isBlank()) {
                    bindingDialog.edtMinute.setText("00")
                }
            }
        }
        bindingDialog.edtHour.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = bindingDialog.edtHour.text.toString()
                if (text.length == 1) {
                    // Nếu chỉ có một chữ số, thêm số 0 vào trước
                    bindingDialog.edtHour.setText("0$text")
                } else if (text.isBlank()) {
                    bindingDialog.edtHour.setText("00")
                }
            }
        }
        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.btnSettime.setOnClickListener {

            val minute = when (bindingDialog.edtMinute.text.toString().length) {
                1 -> {
                    "0" + bindingDialog.edtMinute.text.toString()
                }
                0 -> {
                    "00"
                }
                else -> {
                    bindingDialog.edtMinute.text.toString()
                }
            }

            val hour = when (bindingDialog.edtHour.text.toString().length) {
                1 -> {
                    "0" + bindingDialog.edtHour.text.toString()
                }
                0 -> {
                    "00"
                }
                else -> {
                    bindingDialog.edtHour.text.toString()
                }
            }
            setDailyReminder(
                Common.convertHourToString(
                    hour,
                    minute,
                    isAMSelect
                )
            )
            setReminder()
            binding.tvTime.text = getDailyReminder()
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun showPopupRepeat() {
        val bindingPopup = PopupRepeatBinding.inflate(layoutInflater)

        val popupWindow = PopupWindow(this)
        popupWindow.contentView = bindingPopup.root
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        when (getRepeat()) {
            1 -> bindingPopup.icDonotrepeat.setImageResource(R.drawable.ic_checked)
            2 -> bindingPopup.icEveryday.setImageResource(R.drawable.ic_checked)
            3 -> bindingPopup.icWeekly.setImageResource(R.drawable.ic_checked)
            4 -> bindingPopup.icMonthly.setImageResource(R.drawable.ic_checked)
            5 -> bindingPopup.icYearly.setImageResource(R.drawable.ic_checked)
        }

        bindingPopup.btnDonotrepeat.setOnClickListener {
            setRepeat(1)
            binding.tvRepeat.text = getText(R.string.do_not_repeat)
            popupWindow.dismiss()
        }
        bindingPopup.btnEveryday.setOnClickListener {
            setRepeat(2)
            binding.tvRepeat.text = getText(R.string.every_day)
            popupWindow.dismiss()
        }
        bindingPopup.btnWeekly.setOnClickListener {
            setRepeat(3)
            binding.tvRepeat.text = getText(R.string.weekly)
            popupWindow.dismiss()
        }
        bindingPopup.btnMonthly.setOnClickListener {
            setRepeat(4)
            binding.tvRepeat.text = getText(R.string.monthly)
            popupWindow.dismiss()
        }
        bindingPopup.btnYearly.setOnClickListener {
            setRepeat(5)
            binding.tvRepeat.text = getText(R.string.yearly)
            popupWindow.dismiss()
        }
        popupWindow.showAsDropDown(binding.icDown)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.getRootView().windowToken, 0)
    }
}