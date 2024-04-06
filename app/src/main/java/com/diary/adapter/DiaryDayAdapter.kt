package com.diary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.Common.convertStringToCalendar
import com.diary.R
import com.diary.database.DiaryEntry
import java.text.SimpleDateFormat
import java.util.Calendar


class DiaryDayAdapter(private val context: Context, private val diaryEntries: List<DiaryEntry>) :
    RecyclerView.Adapter<DiaryDayAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_diary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diaryEntry = diaryEntries[position]
        if (diaryEntry.title.isNotBlank()) {
            holder.tvTitle.text = diaryEntry.title
        } else {
            holder.tvTitle.text = context.getString(R.string.notitle)
        }

        val calendar = diaryEntry.timeCreate.convertStringToCalendar()

        val daysOfWeek = arrayOf(
            context.getString(R.string.sunday),
            context.getString(R.string.monday),
            context.getString(R.string.tuesday),
            context.getString(R.string.wednesday),
            context.getString(R.string.thursday),
            context.getString(R.string.friday),
            context.getString(R.string.saturday)
        )
        val simpleDateFormat = SimpleDateFormat("hh:mm a")

        val time: String = simpleDateFormat.format(calendar.time)

        holder.tvTime.text = daysOfWeek.get(calendar.get(Calendar.DAY_OF_WEEK) - 1) + "  " + time

        when (diaryEntry.emotion) {
            0 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_1)
            1 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_2)
            2 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_3)
            3 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_4)
            4 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_5)
            5 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_6)
            6 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_7)
        }
    }

    override fun getItemCount(): Int {
        return diaryEntries.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvTime: TextView
        var ivEmotion: ImageView

        init {
            tvTitle = itemView.findViewById<TextView>(R.id.tv_title_diary_day)
            tvTime = itemView.findViewById<TextView>(R.id.tv_time_diary_day)
            ivEmotion = itemView.findViewById<ImageView>(R.id.img_emotion_diary_day)
        }
    }
}