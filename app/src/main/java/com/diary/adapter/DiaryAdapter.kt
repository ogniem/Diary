package com.diary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.Common.gone
import com.diary.Common.invisible
import com.diary.Common.visible
import com.diary.R
import com.diary.database.DiaryEntry
import java.util.Arrays
import java.util.Date


class DiaryAdapter(private val diaryDays: List<Date>) :
    RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position
        val diaryDay = diaryDays[position]
        holder.tvDay.text = "14"
        holder.tvMonth.text = "Jan"
        holder.tvYear.text = "2024"

        // Set DiaryDayAdapter
        val diaryDayAdapter = DiaryDayAdapter(generateDiaryEntries())
        holder.rvDiaryDays.setAdapter(diaryDayAdapter)

        if (pos == diaryDays.size-1){
            holder.viewSpace.invisible()
        }else{
            holder.viewSpace.gone()
        }
    }

    override fun getItemCount(): Int {
        return diaryDays.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay: TextView
        var tvMonth: TextView
        var tvYear: TextView
        var rvDiaryDays: RecyclerView
        var viewSpace: View

        init {
            tvDay = itemView.findViewById<TextView>(R.id.tv_day_diary)
            tvMonth = itemView.findViewById<TextView>(R.id.tv_month_diary)
            tvYear = itemView.findViewById<TextView>(R.id.tv_year_diary)
            rvDiaryDays = itemView.findViewById<RecyclerView>(R.id.rcv_diary_day)
            viewSpace = itemView.findViewById<View>(R.id.view_space)
        }
    }

    private fun getDiaryDays(date: Date): List<DiaryEntry> {
        return Arrays.asList(DiaryEntry())
    }
    fun generateDiaryEntries(): List<DiaryEntry> {
        val diaryEntries: MutableList<DiaryEntry> = ArrayList()

        // Thêm dữ liệu mẫu
        diaryEntries.add(
            DiaryEntry(
                "Ngày 1",
                "Thứ Hai",
                1,
                "Hello",
                mutableListOf("https://picsum.photos/200/300").toTypedArray(),
                "ngayf đẹp"
            )
        )
        return diaryEntries
    }
}

