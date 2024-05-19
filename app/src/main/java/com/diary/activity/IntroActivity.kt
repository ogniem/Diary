package com.diary.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diary.R
import com.diary.databinding.ActivityIntroBinding


class IntroActivity : BaseActivity() {
    private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = IntroAdapter(this)
        binding.vp.adapter = adapter
        binding.dotindicator.attachTo(binding.vp)
        binding.btnContinue.setOnClickListener {
            if (binding.vp.currentItem < 2) {
                binding.vp.currentItem += 1
            } else {
                startActivity(Intent(this, NameActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (binding.vp.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.vp.currentItem -= 1
        }
    }

    class IntroAdapter(private val context: Context) :
        RecyclerView.Adapter<IntroAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.intro_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            when (position) {
                0 -> {
                    holder.textView.text = context.getText(R.string.intro1)
                    holder.imageView.setImageResource(R.drawable.intro1)
                }

                1 -> {
                    holder.textView.text = context.getText(R.string.intro2)
                    holder.imageView.setImageResource(R.drawable.intro2)
                }

                2 -> {
                    holder.textView.text = context.getText(R.string.intro3)
                    holder.imageView.setImageResource(R.drawable.intro3)
                }
            }
        }

        override fun getItemCount(): Int {
            return 3
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.tv_intro)
            val imageView: ImageView = itemView.findViewById(R.id.img_intro)
        }
    }

}