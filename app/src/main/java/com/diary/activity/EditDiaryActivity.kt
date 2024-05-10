package com.diary.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.diary.Common
import com.diary.Common.gone
import com.diary.Common.visible
import com.diary.R
import com.diary.database.DiaryDatabase
import com.diary.database.DiaryEntry
import com.diary.databinding.ActivityCreateDiaryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDiaryActivity : BaseActivity() {
    private val binding by lazy { ActivityCreateDiaryBinding.inflate(layoutInflater) }
    private var diary = DiaryEntry()
    private var isEditting = false
    private var image1 = ""
    private var image2 = ""
    private var image3 = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val diaryEntryDao = DiaryDatabase.getInstance(this).diaryEntryDao()
        val id = intent.getIntExtra(Common.KEY_POSITION_DIARY, -1)
        if(id == -1){
            Toast.makeText(this, " Trống", Toast.LENGTH_LONG).show()
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
                    diary = it

                    if(!diary.imageLink1.isNullOrBlank()){
                        image1 = diary.imageLink1
                        Glide.with(this@EditDiaryActivity).load(image1).into(binding.img1)
                    }

                    if(!diary.imageLink2.isNullOrBlank()){
                        image2 = diary.imageLink2
                        Glide.with(this@EditDiaryActivity).load(image2).into(binding.img2)
                    }
                    if(!diary.imageLink3.isNullOrBlank()){
                        image3 = diary.imageLink3
                        Glide.with(this@EditDiaryActivity).load(image3).into(binding.img3)
                    }

                    binding.edtTitle.setText(diary.title)
                    binding.edtContent.setText(diary.content)

                    when (diary.emotion) {
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
        }

        binding.btnBack.setOnClickListener {
            if (isEditting) {
                isEditting = false
            } else {
                finish()
            }
        }
    }

    private fun insertImageToList(image: Uri) {
        if (image1.isBlank()) {
            image1 = image.toString()
            Glide.with(this).load(image).into(binding.img1)
            binding.btnDelete1.visible()
        } else {
            if (image2.isBlank()) {
                image2 = image.toString()
                Glide.with(this).load(image).into(binding.img2)
                binding.btnDelete2.visible()
            } else {
                if (image3.isBlank()) {
                    image3 = image.toString()
                    Glide.with(this).load(image).into(binding.img3)
                    binding.btnDelete3.visible()
                }
            }
        }
    }

    private fun removeImageOutList(indexImage: Int) {
        when (indexImage) {
            1 -> {
                if (image2.isNotBlank()) {
                    image1 = image2
                    Glide.with(this).load(Uri.parse(image1)).into(binding.img1)
                    if (image3.isNotBlank()) {
                        image2 = image3
                        Glide.with(this).load(Uri.parse(image2)).into(binding.img2)
                        image3 = ""
                        binding.img3.setImageResource(R.drawable.img_empty)
                        binding.btnDelete3.gone()
                    } else {
                        image2 = ""
                        binding.img2.setImageResource(R.drawable.img_empty)
                        binding.btnDelete2.gone()
                    }

                } else {
                    image1 = ""
                    binding.img1.setImageResource(R.drawable.img_empty)
                    binding.btnDelete1.gone()
                }
            }

            2 -> {
                if (image3.isNotBlank()) {
                    image2 = image3
                    Glide.with(this).load(Uri.parse(image2)).into(binding.img2)
                    image3 = ""
                    binding.img3.setImageResource(R.drawable.img_empty)
                    binding.btnDelete3.gone()
                } else {
                    image2 = ""
                    binding.img2.setImageResource(R.drawable.img_empty)
                    binding.btnDelete2.gone()
                }
            }

            3 -> {
                image3 = ""
                binding.img3.setImageResource(R.drawable.img_empty)
                binding.btnDelete3.gone()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(!isEditting){
            binding.edtTitle.isEnabled = false
            binding.edtTitle.isFocusable = false
            binding.edtTitle.isFocusableInTouchMode = false

            binding.edtContent.isEnabled = false
            binding.edtContent.isFocusable = false
            binding.edtContent.isFocusableInTouchMode = false
        }
    }
}