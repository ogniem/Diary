package com.diary.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.diary.Common.convertCalendarToString
import com.diary.Common.gone
import com.diary.Common.visible
import com.diary.R
import com.diary.database.DiaryDatabase
import com.diary.database.DiaryEntry
import com.diary.databinding.ActivityCreateDiaryBinding
import com.diary.databinding.DialogChooseTypeImageBinding
import com.diary.databinding.DialogDeleteImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.Calendar


class CreateDiaryActivity : BaseActivity() {
    private val binding by lazy { ActivityCreateDiaryBinding.inflate(layoutInflater) }
    var image1 = ""
    var image2 = ""
    var image3 = ""
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
            if (image1.isBlank()) {
                showBottomDialog()
            }
        }
        binding.img2.setOnClickListener {
            if (image2.isBlank()) {
                showBottomDialog()
            }
        }
        binding.img3.setOnClickListener {
            if (image3.isBlank()) {
                showBottomDialog()
            }
        }

        binding.btnDelete1.setOnClickListener {
            showDialogDeleteImage(1)
        }
        binding.btnDelete2.setOnClickListener {
            showDialogDeleteImage(2)
        }
        binding.btnDelete3.setOnClickListener {
            showDialogDeleteImage(3)
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
                    diaryEntry.imageLink1 = image1
                    diaryEntry.imageLink2 = image2
                    diaryEntry.imageLink3 = image3
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

    private fun showBottomDialog() {
        val bindingDialog = DialogChooseTypeImageBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bindingDialog.root)
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        bindingDialog.btnChooseimage.setOnClickListener {
            chooseImage()
            dialog.dismiss()
        }
        bindingDialog.btnTakephoto.setOnClickListener {
            takePhoto()
            dialog.dismiss()
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

    fun getImageUri(inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            bytes
        ) // Used for compression rate of the Image : 100 means no compression
        val path = Images.Media.insertImage(contentResolver, inImage, "xyz", null)
        return Uri.parse(path)
    }

    private fun showDialogDeleteImage(indexImage: Int) {
        val bindingDialog = DialogDeleteImageBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(bindingDialog.root)
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.btnYes.setOnClickListener {
            removeImageOutList(indexImage)
            dialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            insertImageToList(imageUri!!)
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val imageUri = data.extras?.get("data") as? Bitmap
            insertImageToList(getImageUri(imageUri!!))
        }
    }
}