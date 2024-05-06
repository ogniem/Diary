package com.diary.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.transition.TransitionManager
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diary.Common.invisible
import com.diary.Common.visible
import com.diary.database.Schedule
import com.diary.database.ScheduleDatabase
import com.diary.databinding.ActivityMainBinding
import com.diary.databinding.DialogCreateScheduleBinding
import com.diary.fragment.ScheduleFragment
import com.diary.fragment.DiaryFragment
import com.diary.fragment.ReportFragment
import com.diary.fragment.SettingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val scheduleFragment = ScheduleFragment()
    private var fragSelect = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (intent.getBooleanExtra("IS_SETTING", false)) {
            selectFragment(4)
        } else {
            selectFragment(1)
        }

        binding.btnDiary.setOnClickListener {
            selectFragment(1)
        }
        binding.btnSchedule.setOnClickListener {
            selectFragment(2)
        }
        binding.btnReport.setOnClickListener {
            selectFragment(3)
        }
        binding.btnSetting.setOnClickListener {
            selectFragment(4)
        }

        binding.btnAdd.setOnClickListener {
            if (fragSelect == 2) {
                showAddDialog()
            } else {
                startActivity(Intent(this, AddDiaryActivity::class.java))
            }
        }
    }

    private fun selectFragment(fragSelected: Int) {
        fragSelect = fragSelected
        TransitionManager.beginDelayedTransition(binding.main)
        when (fragSelected) {
            1 -> {
                binding.icDiary.setColorFilter(Color.parseColor("#383655"))
                binding.dotDiary.visible()
                binding.icCalendar.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotCalendar.invisible()
                binding.icReport.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotReport.invisible()
                binding.icSetting.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotSetting.invisible()

                replaceFragment(DiaryFragment())
            }

            2 -> {
                binding.icDiary.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotDiary.invisible()
                binding.icCalendar.setColorFilter(Color.parseColor("#383655"))
                binding.dotCalendar.visible()
                binding.icReport.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotReport.invisible()
                binding.icSetting.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotSetting.invisible()
                replaceFragment(scheduleFragment)
            }

            3 -> {
                binding.icDiary.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotDiary.invisible()
                binding.icCalendar.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotCalendar.invisible()
                binding.icReport.setColorFilter(Color.parseColor("#383655"))
                binding.dotReport.visible()
                binding.icSetting.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotSetting.invisible()
                replaceFragment(ReportFragment())
            }

            4 -> {
                binding.icDiary.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotDiary.invisible()
                binding.icCalendar.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotCalendar.invisible()
                binding.icReport.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotReport.invisible()
                binding.icSetting.setColorFilter(Color.parseColor("#383655"))
                binding.dotSetting.visible()
                replaceFragment(SettingFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fragContainer.id, fragment)
        transaction.commit()
    }

    private fun showAddDialog() {
        val bindingDialog = DialogCreateScheduleBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(bindingDialog.root)
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        bindingDialog.btnYes.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val database = ScheduleDatabase.getInstance(this@MainActivity)
                    val dao = database.scheduleDao()
                    val schedule = Schedule()
                    schedule.title = bindingDialog.edtTitle.text.toString()
                    schedule.content = bindingDialog.edtContent.text.toString()
                    schedule.time =
                        bindingDialog.nbHour.value.toString() + ":" + bindingDialog.nbMinute.value.toString() + " AM"
                    schedule.isReminder = bindingDialog.swReminder.isChecked
                    schedule.dayOfWeek = scheduleFragment.currentDay
                    dao.insertSchedule(schedule)
                    dialog.dismiss()
                    scheduleFragment.loadListSchedule()
                }
            }
        }
        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

}