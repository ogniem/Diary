package com.diary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diary.Common.visible
import com.diary.database.Schedule
import com.diary.databinding.ItemScheduleBinding


class ScheduleAdapter(
    private val context: Context,
    private var schedules: MutableList<Schedule>,
) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemScheduleBinding = ItemScheduleBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    inner class ViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val schedule = schedules[pos]
            binding.tvTitle.text = schedule.title
            binding.tvContent.text = schedule.content
            binding.tvTime.text = schedule.time
            if (schedule.isReminder) {
                binding.btnReminder.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#FF0000"))
            } else {
                binding.btnReminder.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#80000000"))
            }
            if (pos == schedules.size - 1){
                binding.viewSpace.visible()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(listschedule: MutableList<Schedule>){
        schedules.clear()
        schedules.addAll(listschedule)
        notifyDataSetChanged()
    }
}

