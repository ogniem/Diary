package com.diary.fragment

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diary.Common.gone
import com.diary.Common.visible
import com.diary.R
import com.diary.adapter.ScheduleAdapter
import com.diary.database.Schedule
import com.diary.database.ScheduleDatabase
import com.diary.databinding.FragmentScheduleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class ScheduleFragment : Fragment() {

    private val binding by lazy { FragmentScheduleBinding.inflate(layoutInflater) }
    private var adapter: ScheduleAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ScheduleAdapter(requireContext(), mutableListOf())

        binding.rcvSchedule.adapter = adapter

        val dayOfWeek: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val day = when (dayOfWeek) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            Calendar.SUNDAY -> 7
            else -> 1
        }

        selectDayOfWeek(day)



        binding.btnMon.setOnClickListener {
            selectDayOfWeek(1)
        }
        binding.btnTue.setOnClickListener {
            selectDayOfWeek(2)
        }
        binding.btnWed.setOnClickListener {
            selectDayOfWeek(3)
        }
        binding.btnThu.setOnClickListener {
            selectDayOfWeek(4)
        }
        binding.btnFri.setOnClickListener {
            selectDayOfWeek(5)
        }
        binding.btnSat.setOnClickListener {
            selectDayOfWeek(6)
        }
        binding.btnSun.setOnClickListener {
            selectDayOfWeek(7)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun selectDayOfWeek(pos: Int) {
        TransitionManager.beginDelayedTransition(binding.root)
        getListSchedule(pos)
        when (pos) {
            1 -> {
                binding.btnMon.setTextColor(Color.parseColor("#383655"))
                binding.btnMon.setBackgroundResource(R.drawable.bg_day_of_week_select)
                binding.btnTue.setTextColor(Color.WHITE)
                binding.btnTue.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnWed.setTextColor(Color.WHITE)
                binding.btnWed.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnThu.setTextColor(Color.WHITE)
                binding.btnThu.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnFri.setTextColor(Color.WHITE)
                binding.btnFri.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSat.setTextColor(Color.WHITE)
                binding.btnSat.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSun.setTextColor(Color.WHITE)
                binding.btnSun.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
            }

            2 -> {
                binding.btnMon.setTextColor(Color.WHITE)
                binding.btnMon.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnTue.setTextColor(Color.parseColor("#383655"))
                binding.btnTue.setBackgroundResource(R.drawable.bg_day_of_week_select)
                binding.btnWed.setTextColor(Color.WHITE)
                binding.btnWed.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnThu.setTextColor(Color.WHITE)
                binding.btnThu.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnFri.setTextColor(Color.WHITE)
                binding.btnFri.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSat.setTextColor(Color.WHITE)
                binding.btnSat.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSun.setTextColor(Color.WHITE)
                binding.btnSun.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
            }

            3 -> {
                binding.btnMon.setTextColor(Color.WHITE)
                binding.btnMon.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnTue.setTextColor(Color.WHITE)
                binding.btnTue.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnWed.setTextColor(Color.parseColor("#383655"))
                binding.btnWed.setBackgroundResource(R.drawable.bg_day_of_week_select)
                binding.btnThu.setTextColor(Color.WHITE)
                binding.btnThu.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnFri.setTextColor(Color.WHITE)
                binding.btnFri.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSat.setTextColor(Color.WHITE)
                binding.btnSat.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSun.setTextColor(Color.WHITE)
                binding.btnSun.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
            }

            4 -> {
                binding.btnMon.setTextColor(Color.WHITE)
                binding.btnMon.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnTue.setTextColor(Color.WHITE)
                binding.btnTue.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnWed.setTextColor(Color.WHITE)
                binding.btnWed.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnThu.setTextColor(Color.parseColor("#383655"))
                binding.btnThu.setBackgroundResource(R.drawable.bg_day_of_week_select)
                binding.btnFri.setTextColor(Color.WHITE)
                binding.btnFri.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSat.setTextColor(Color.WHITE)
                binding.btnSat.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSun.setTextColor(Color.WHITE)
                binding.btnSun.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
            }

            5 -> {
                binding.btnMon.setTextColor(Color.WHITE)
                binding.btnMon.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnTue.setTextColor(Color.WHITE)
                binding.btnTue.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnWed.setTextColor(Color.WHITE)
                binding.btnWed.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnThu.setTextColor(Color.WHITE)
                binding.btnThu.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnFri.setTextColor(Color.parseColor("#383655"))
                binding.btnFri.setBackgroundResource(R.drawable.bg_day_of_week_select)
                binding.btnSat.setTextColor(Color.WHITE)
                binding.btnSat.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSun.setTextColor(Color.WHITE)
                binding.btnSun.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
            }

            6 -> {
                binding.btnMon.setTextColor(Color.WHITE)
                binding.btnMon.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnTue.setTextColor(Color.WHITE)
                binding.btnTue.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnWed.setTextColor(Color.WHITE)
                binding.btnWed.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnThu.setTextColor(Color.WHITE)
                binding.btnThu.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnFri.setTextColor(Color.WHITE)
                binding.btnFri.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSat.setTextColor(Color.parseColor("#383655"))
                binding.btnSat.setBackgroundResource(R.drawable.bg_day_of_week_select)
                binding.btnSun.setTextColor(Color.WHITE)
                binding.btnSun.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
            }

            7 -> {
                binding.btnMon.setTextColor(Color.WHITE)
                binding.btnMon.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnTue.setTextColor(Color.WHITE)
                binding.btnTue.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnWed.setTextColor(Color.WHITE)
                binding.btnWed.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnThu.setTextColor(Color.WHITE)
                binding.btnThu.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnFri.setTextColor(Color.WHITE)
                binding.btnFri.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSat.setTextColor(Color.WHITE)
                binding.btnSat.setBackgroundResource(R.drawable.bg_day_of_week_unselect)
                binding.btnSun.setTextColor(Color.parseColor("#383655"))
                binding.btnSun.setBackgroundResource(R.drawable.bg_day_of_week_select)
            }
        }
    }

    private fun getListSchedule(pos: Int) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val database = ScheduleDatabase.getInstance(requireContext())
                val dao = database.scheduleDao()
                val listSchedule = dao.getSchedulesByDayOfWeek(pos)
                Log.d("TAG123", "getListSchedule: " + listSchedule)
                if (listSchedule.size == 0) {
                    binding.rcvSchedule.gone()
                    binding.imgListEmpty.visible()
                } else {
                    binding.rcvSchedule.visible()
                    binding.imgListEmpty.gone()
                    adapter?.changeList(listSchedule.toMutableList())
                }
            }
        }
    }
}