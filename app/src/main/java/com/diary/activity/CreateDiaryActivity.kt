package com.diary.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diary.R
import com.diary.databinding.ActivityCreateDiaryBinding
import java.util.Calendar

class CreateDiaryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreateDiaryBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var timeCreate = Calendar.getInstance()
        if (intent.extras != null) {
            timeCreate = intent.extras?.getSerializable("myCalendar") as Calendar
        }

        val emotion = intent.getIntExtra("EMOTION", 5)

        when (emotion) {
            0 -> {
                binding.tvEmotion.text = getText(R.string.emotion_1)
                binding.imgEmotion.setImageResource(R.drawable.ic_emotion_1)
            }

            1 -> {
                binding.tvEmotion.text = getText(R.string.emotion_2)
                binding.imgEmotion.setImageResource(R.drawable.ic_emotion_2)
            }

            2 -> {
                binding.tvEmotion.text = getText(R.string.emotion_3)
                binding.imgEmotion.setImageResource(R.drawable.ic_emotion_3)
            }

            3 -> {
                binding.tvEmotion.text = getText(R.string.emotion_4)
                binding.imgEmotion.setImageResource(R.drawable.ic_emotion_4)
            }

            4 -> {
                binding.tvEmotion.text = getText(R.string.emotion_5)
                binding.imgEmotion.setImageResource(R.drawable.ic_emotion_5)
            }

            5 -> {
                binding.tvEmotion.text = getText(R.string.emotion_6)
                binding.imgEmotion.setImageResource(R.drawable.ic_emotion_6)
            }

            6 -> {
                binding.tvEmotion.text = getText(R.string.emotion_7)
                binding.imgEmotion.setImageResource(R.drawable.ic_emotion_7)
            }
        }
    }
}