package com.diary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.Common.convertStringToCalendar
import com.diary.Common.getDay
import com.diary.Common.getMonth
import com.diary.Common.getSort
import com.diary.Common.getYear
import com.diary.Common.gone
import com.diary.Common.invisible
import com.diary.Common.visible
import com.diary.R
import com.diary.database.DiaryEntry
import com.diary.model.Day


@Suppress("DEPRECATION")
class DiaryAdapter(
    private val context: Context,
    private var diaryDays: List<Day>?,
    private val diarylist: List<DiaryEntry>
) :
    RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    private var sortListNewest = context.getSort()

    private val filterlist = mutableListOf<DiaryEntry>()

    init {
        filterlist.clear()
        filterlist.addAll(diarylist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = position
        val diaryDay = if (sortListNewest) {
            diaryDays?.get(position)
        }else{
            diaryDays!!.reversed().get(position)
        }

        val list = getDiaryOnADay(diaryDay!!)
        if (list.isEmpty()) {
            holder.bg.gone()
        } else {
            holder.bg.visible()
        }

        holder.tvDay.text = diaryDay.day.toString()
        holder.tvMonth.text = diaryDay.month.toString()
        holder.tvYear.text = diaryDay.year.toString()


        // Set DiaryDayAdapter
        val diaryDayAdapter = DiaryDayAdapter(context, list)
        holder.rvDiaryDays.setAdapter(diaryDayAdapter)

        if (pos == diaryDays!!.size - 1) {
            holder.viewSpace.invisible()
        } else {
            holder.viewSpace.gone()
        }
    }

    override fun getItemCount(): Int {
        return diaryDays!!.size
    }

    fun filterList(text: String) {
        filterlist.clear()
        if (text.isBlank()) {
            filterlist.addAll(diarylist)
        } else {
            for (diary in diarylist) {
                if (diary.title.toLowerCase()
                        .contains(text.toLowerCase()) || diary.content.toLowerCase()
                        .contains(text.toLowerCase())
                ) {
                    filterlist.add(diary)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun sort(isNewest: Boolean) {
        sortListNewest = isNewest
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay: TextView
        var tvMonth: TextView
        var tvYear: TextView
        var rvDiaryDays: RecyclerView
        var viewSpace: View
        var bg: LinearLayout

        init {
            tvDay = itemView.findViewById(R.id.tv_day_diary)
            tvMonth = itemView.findViewById(R.id.tv_month_diary)
            tvYear = itemView.findViewById(R.id.tv_year_diary)
            rvDiaryDays = itemView.findViewById(R.id.rcv_diary_day)
            viewSpace = itemView.findViewById(R.id.view_space)
            bg = itemView.findViewById(R.id.bg_itemm)
        }
    }

    private fun getDiaryOnADay(day: Day): List<DiaryEntry> {
        val listDiaryInADay = mutableListOf<DiaryEntry>()
        if (filterlist != null) {
            for (diary in filterlist) {
                val calendar = diary?.timeCreate?.convertStringToCalendar()
                if (calendar?.getDay() == day.day && calendar.getMonth() == day.month && calendar.getYear() == day.year) {
                    listDiaryInADay.add(diary)
                }
            }
        }

        return listDiaryInADay
    }
}

