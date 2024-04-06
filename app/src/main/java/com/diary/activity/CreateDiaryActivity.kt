package com.diary.activity

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.diary.Common.convertCalendarToString
import com.diary.R
import com.diary.database.DiaryDatabase
import com.diary.database.DiaryEntry
import com.diary.databinding.ActivityCreateDiaryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


class CreateDiaryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreateDiaryBinding.inflate(layoutInflater) }
    var image = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var timeCreate = Calendar.getInstance()
        if (intent.extras != null) {
            timeCreate = intent.extras?.getSerializable("TIME_CREATE") as Calendar
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

        binding.img1.setOnClickListener {
            takePhoto()
        }

        binding.btnCreate.setOnClickListener {

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val database = DiaryDatabase.getInstance(this@CreateDiaryActivity)
                    val dao = database!!.diaryEntryDao()

                    val diaryEntry = DiaryEntry()
                    diaryEntry.timeCreate = timeCreate.convertCalendarToString()
                    diaryEntry.emotion = emotion
                    diaryEntry.title = binding.edtTitle.text.toString()
                    diaryEntry.imageLink1 = image
                    diaryEntry.imageLink2 = image
                    diaryEntry.imageLink3 = image
                    diaryEntry.content = binding.edtContent.text.toString()
                    dao!!.insertDiaryEntry(diaryEntry)
                    Log.d("TAG123", "onCreate: " + dao.allDiaryEntries?.size)
                }
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun chooseImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 1)

    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            Glide.with(this).load(imageUri).into(binding.img1)
            image = imageUri.toString()
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.extras?.get("data")
            Glide.with(this).load(imageUri).into(binding.img1)
            image = imageUri.toString()
        }
    }
}