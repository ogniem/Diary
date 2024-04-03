package com.diary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.R
import com.diary.database.DiaryEntry


class DiaryDayAdapter(private val diaryEntries: List<DiaryEntry>) :
    RecyclerView.Adapter<DiaryDayAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_diary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diaryEntry = diaryEntries[position]
        holder.tvTitle.text = "Helllo"
        holder.tvTime.text = "adfgasdgasdg thứ 2"
//        when (diaryEntry.emotion) {
//            1 -> holder.ivEmotion.setImageResource(R.drawable.ic_emot   ion_happy)
//            2 -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_sad)
//            else -> holder.ivEmotion.setImageResource(R.drawable.ic_emotion_neutral)
//        }
        holder.ivEmotion.setImageResource(R.drawable.ic_emotion_1)
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