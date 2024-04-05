package com.diary.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.WindowManager
import android.widget.CalendarView.OnDateChangeListener
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
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
                when (progress) {
                    0 -> {
                        binding.tvEmotion.text = getText(R.string.emotion_1)
                        binding.imgEmotion.setImageResource(R.drawable.ic_emotion_1)
                    }

                    1 -> {
                        binding.tvEmotion.text = getText(R.string.emotion_2)
                        binding.imgEmotion.setImageResource(R.drawable.ic_emotion_2)
                    }

                    2 -> {
                        binding.tvEmotion.text = getText(R.string.emotion_3)
                        binding.imgEmotion.setImageResource(R.drawable.ic_emotion_3)
                    }

                    3 -> {
                        binding.tvEmotion.text = getText(R.string.emotion_4)
                        binding.imgEmotion.setImageResource(R.drawable.ic_emotion_4)
                    }

                    4 -> {
                        binding.tvEmotion.text = getText(R.string.emotion_5)
                        binding.imgEmotion.setImageResource(R.drawable.ic_emotion_5)
                    }

                    5 -> {
                        binding.tvEmotion.text = getText(R.string.emotion_6)
                        binding.imgEmotion.setImageResource(R.drawable.ic_emotion_6)
                    }

                    6 -> {
                        binding.tvEmotion.text = getText(R.string.emotion_7)
                        binding.imgEmotion.setImageResource(R.drawable.ic_emotion_7)
                    }
                }
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

            bindingDialog.calendar.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
                daySelect = dayOfMonth
                monthSelect = month
                yearSelect = year
            })

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
}