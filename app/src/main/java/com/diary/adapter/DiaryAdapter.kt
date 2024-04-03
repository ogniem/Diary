package com.diary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.R
import com.diary.database.DiaryEntry
import java.util.Arrays
import java.util.Date


class DiaryAdapter(private val diaryDay: List<Date>) :
    RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diaryDay = diaryDay[position]
        holder.tvDay.text = "14"
        holder.tvMonth.text = "Jan"
        holder.tvYear.text = "2024"

        // Set DiaryDayAdapter
        val diaryDays = getDiaryDays(diaryDay)
        val diaryDayAdapter = DiaryDayAdapter(diaryDays)
        holder.rvDiaryDays.setAdapter(diaryDayAdapter)
    }

    override fun getItemCount(): Int {
        return diaryDay.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay: TextView
        var tvMonth: TextView
        var tvYear: TextView
        var rvDiaryDays: RecyclerView

        init {
            tvDay = itemView.findViewById<TextView>(R.id.tv_day_diary)
            tvMonth = itemView.findViewById<TextView>(R.id.tv_month_diary)
            tvYear = itemView.findViewById<TextView>(R.id.tv_year_diary)
            rvDiaryDays = itemView.findViewById<RecyclerView>(R.id.rcv_diary_day)
        }
    }

    private fun getDiaryDays(date: Date): List<DiaryEntry> {
        return Arrays.asList(DiaryEntry())
    }
}

