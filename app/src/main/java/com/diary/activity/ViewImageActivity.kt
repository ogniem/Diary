package com.diary.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.diary.Common
import com.diary.R
import com.diary.database.DiaryDatabase
import com.diary.database.DiaryEntry
import com.diary.databinding.ActivityViewImageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewImageActivity : BaseActivity() {
    private val binding by lazy { ActivityViewImageBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var diaryEntry = DiaryEntry()

        val diaryEntryDao = DiaryDatabase.getInstance(this).diaryEntryDao()
        val id = intent.getIntExtra("KEY_POSITION_DIARY", -1)
        val imageIndex = intent.getIntExtra("KEY_LINK_IMAGE", 1)
        if (id == -1) {
            finish()
            return
        }

        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                val diaryyy = diaryEntryDao.getDiaryEntryById(id)
                diaryyy
            }
            withContext(Dispatchers.Main) {
                result.let {
                    Log.d("TAG123", "onCreate: "+it.imageLink1)
                    Glide.with(this@ViewImageActivity).load(it.imageLink1).into(binding.img)
                    when (imageIndex) {
                        1 -> Glide.with(this@ViewImageActivity).load(it.imageLink1)
                            .into(binding.img)

                        2 -> Glide.with(this@ViewImageActivity).load(it.imageLink2)
                            .into(binding.img)

                        3 -> Glide.with(this@ViewImageActivity).load(it.imageLink3)
                            .into(binding.img)
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    if (imageIndex == 3) {
                        diaryEntry.imageLink3 = ""
                    } else if (imageIndex == 2) {
                        diaryEntry.imageLink2 = diaryEntry.imageLink3
                        diaryEntry.imageLink3 = ""
                    } else {
                        diaryEntry.imageLink1 = diaryEntry.imageLink2
                        diaryEntry.imageLink2 = diaryEntry.imageLink3
                        diaryEntry.imageLink3 = ""
                    }

                    diaryEntryDao.updateDiaryEntry(diaryEntry)
                    finish()
                }
            }
        }
    }
}