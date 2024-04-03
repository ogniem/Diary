package com.diary.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import com.diary.Common.invisible
import com.diary.Common.visible
import com.diary.databinding.ActivityMainBinding
import com.diary.fragment.CalendarFragment
import com.diary.fragment.DiaryFragment
import com.diary.fragment.ReportFragment
import com.diary.fragment.SettingFragment


class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        selectFragment(1)

        binding.btnDiary.setOnClickListener {
            selectFragment(1)
        }
        binding.btnCalendar.setOnClickListener {
            selectFragment(2)
        }
        binding.btnReport.setOnClickListener {
            selectFragment(3)
        }
        binding.btnSetting.setOnClickListener {
            selectFragment(4)
        }

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, AddDiaryActivity::class.java))
        }
    }

    private fun selectFragment(fragSelected: Int) {
        TransitionManager.beginDelayedTransition(binding.main)
        when (fragSelected) {
            1 ->{
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
            2 ->{
                binding.icDiary.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotDiary.invisible()
                binding.icCalendar.setColorFilter(Color.parseColor("#383655"))
                binding.dotCalendar.visible()
                binding.icReport.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotReport.invisible()
                binding.icSetting.setColorFilter(Color.parseColor("#A3A3A3"))
                binding.dotSetting.invisible()
                replaceFragment(CalendarFragment())
            }
            3 ->{
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
            4 ->{
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



}