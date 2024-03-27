package com.diary.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.Common
import com.diary.R

class LanguageAdapter(private val context: Context) :
    RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    private var selectedItem = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Common.getListLanguages()[position]
        holder.tv_name.text = context.getText(item.name)
        holder.img_lang.setImageResource(item.image)
        if (position == selectedItem) {
            holder.bg_lang.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#766DE6"))
            holder.tv_name.setTypeface(null, Typeface.BOLD)
            holder.tv_name.setTextColor(Color.WHITE)
        }else{
            holder.bg_lang.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
            holder.tv_name.setTypeface(null, Typeface.NORMAL)
            holder.tv_name.setTextColor(Color.BLACK)
        }
        var pos = position
        holder.bg_lang.setOnClickListener {
            var a = selectedItem
            selectedItem = pos
            notifyItemChanged(a)
            notifyItemChanged(selectedItem)
        }
    }

    fun getSelected(): Int {
        return selectedItem
    }

    override fun getItemCount(): Int {
        return Common.getListLanguages().size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name: TextView = itemView.findViewById(R.id.tv_item_lang)
        val img_lang: ImageView = itemView.findViewById(R.id.img_item_lang)
        val bg_lang: LinearLayout = itemView.findViewById(R.id.bg_item_lang)
    }
}
