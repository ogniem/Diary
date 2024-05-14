package com.diary.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionManager
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.diary.Common.getIconByEmotion
import com.diary.Common.getTextByEmotion
import com.diary.Common.getThemeHome
import com.diary.R
import com.diary.databinding.ActivityAddDiaryBinding
import com.diary.databinding.DialogCalendarBinding
import java.util.Calendar


class AddDiaryActivity : BaseActivity() {
    private val binding by lazy { ActivityAddDiaryBinding.inflate(layoutInflater) }
    private var timeCreate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.sbEmotion.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                TransitionManager.beginDelayedTransition(binding.main)
                binding.tvEmotion.text = getTextByEmotion(progress)
                binding.imgEmotion.setImageResource(getIconByEmotion(progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })


        binding.btnCalendar.setOnClickListener {
            val bindingDialog = DialogCalendarBinding.inflate(layoutInflater)
            val popupWindow = PopupWindow(this)
            popupWindow.setContentView(bindingDialog.root)
            popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
            popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
            popupWindow.isFocusable = true
            popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            popupWindow.showAsDropDown(binding.btnCalendar)


            var daySelect = timeCreate.get(Calendar.DAY_OF_MONTH)
            var monthSelect = timeCreate.get(Calendar.MONTH)
            var yearSelect = timeCreate.get(Calendar.YEAR)

            bindingDialog.calendar.date = timeCreate.timeInMillis

            bindingDialog.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                daySelect = dayOfMonth
                monthSelect = month
                yearSelect = year
            }

            bindingDialog.btnCancelPopup.setOnClickListener {
                popupWindow.dismiss()
            }

            bindingDialog.btnSetdatePopup.setOnClickListener {
                timeCreate.set(Calendar.YEAR, yearSelect)
                timeCreate.set(Calendar.MONTH, monthSelect)
                timeCreate.set(Calendar.DAY_OF_MONTH, daySelect)
                popupWindow.dismiss()
            }
        }

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, CreateDiaryActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("TIME_CREATE", timeCreate)
            intent.putExtras(bundle)
            intent.putExtra("EMOTION", binding.sbEmotion.progress)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        when(getThemeHome()){
            1-> binding.bgTop.setImageResource(R.drawable.img_add_1)
            2-> binding.bgTop.setImageResource(R.drawable.img_add_2)
            3-> binding.bgTop.setImageResource(R.drawable.img_add_3)
            4-> binding.bgTop.setImageResource(R.drawable.img_add_4)
        }
    }
}