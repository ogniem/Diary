package com.diary.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.diary.Common
import com.diary.Common.findItemById
import com.diary.Common.getIconByEmotion
import com.diary.Common.getTextByEmotion
import com.diary.Common.gone
import com.diary.Common.invisible
import com.diary.Common.visible
import com.diary.R
import com.diary.database.DiaryDatabase
import com.diary.database.DiaryEntry
import com.diary.database.DiaryRepository
import com.diary.database.DiaryViewModel
import com.diary.databinding.ActivityCreateDiaryBinding
import com.diary.databinding.DialogChooseTypeImageBinding
import com.diary.databinding.DialogDeleteImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class EditDiaryActivity : BaseActivity() {
    private val binding by lazy { ActivityCreateDiaryBinding.inflate(layoutInflater) }
    private var diary = DiaryEntry()
    private var isEditting = false
    private var isShowing = false
    private var image1 = ""
    private var image2 = ""
    private var image3 = ""
    private var imgSelect = 0
    private var emotion = 0
    private lateinit var diaryViewModel: DiaryViewModel

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            insertImageToList(uri.toString())
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            this.contentResolver.takePersistableUriPermission(uri, flag)
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val diaryEntryDao = DiaryDatabase.getInstance(this).diaryEntryDao()
        val diaryRepository = DiaryRepository(diaryEntryDao)
        diaryViewModel = DiaryViewModel(diaryRepository)
        val id = intent.getIntExtra(Common.KEY_POSITION_DIARY, -1)
        if (id == -1) {
            finish()
            return
        }

        binding.btnCreate.invisible()
        binding.btnCreate.text = getText(R.string.edit_diary)

        diaryViewModel.getAllDiary().observe(this) { list ->
            var diaryEntry: DiaryEntry? = findItemById(list, id)

            if (diaryEntry != null) {
                diary = diaryEntry
                if (!diary.imageLink1.isNullOrBlank()) {
                    image1 = diary.imageLink1
                }
                if (!diary.imageLink2.isNullOrBlank()) {
                    image2 = diary.imageLink2
                }
                if (!diary.imageLink3.isNullOrBlank()) {
                    image3 = diary.imageLink3
                }
                emotion = diary.emotion
                loadImage()
                binding.edtTitle.setText(diary.title)
                binding.edtContent.setText(diary.content)
                binding.tvEmotion.text = getTextByEmotion(diary.emotion)
                binding.imgEmotion.setImageResource(getIconByEmotion(diary.emotion))
            } else {
                finish()
            }
        }


        binding.btnDelete.setOnClickListener {
            val bindingDialog = DialogDeleteImageBinding.inflate(layoutInflater)
            val dialog = Dialog(this)
            dialog.setContentView(bindingDialog.root)
            val window = dialog.window
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            bindingDialog.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            if (isShowing) {
                bindingDialog.btnYes.setOnClickListener {
                    removeImageOutList(imgSelect)
                    dialog.dismiss()
                    unShowImage()
                }
            } else {
                bindingDialog.tvTitle.text = getText(R.string.delete_diary)
                bindingDialog.tvContent.visible()

                bindingDialog.btnYes.setOnClickListener {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            diaryEntryDao.deleteDiaryEntry(diary)
                            dialog.dismiss()
                        }
                    }
                }
            }

            dialog.show()
        }

        binding.btnEdit.setOnClickListener {
            isEditting = !isEditting
            enableEdittext()
        }

        binding.img1.setOnClickListener {
            if (image1.isNotBlank()) {
                if (!isEditting) {
                    showImage(1, image1)
                }
            }
        }
        binding.img2.setOnClickListener {
            if (image2.isNotBlank()) {
                if (!isEditting) {
                    showImage(2, image2)
                }
            }

        }
        binding.img3.setOnClickListener {
            if (image3.isNotBlank()) {
                if (!isEditting) {
                    showImage(3, image3)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun insertImageToList(image: String) {
        if (image1.isBlank()) {
            image1 = image
            binding.btnDelete1.visible()
        } else {
            if (image2.isBlank()) {
                image2 = image
                binding.btnDelete2.visible()
            } else {
                if (image3.isBlank()) {
                    image3 = image
                    binding.btnDelete3.visible()
                }
            }
        }
        loadImage()
    }

    private fun removeImageOutList(indexImage: Int) {
        when (indexImage) {
            1 -> {
                image1 = image2
                image2 = image3
                image3 = ""
            }

            2 -> {
                image2 = image3
                image3 = ""
            }

            3 -> {
                image3 = ""
            }
        }
        loadImage()
    }

    private fun chooseImage() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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

    private fun getImageUri(inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            bytes
        ) // Used for compression rate of the Image : 100 means no compression
        val path = MediaStore.Images.Media.insertImage(contentResolver, inImage, "xyz", null)
        return Uri.parse(path)
    }

    private fun loadImage() {
        if (image1.isNotBlank()) {
            Glide.with(this).load(Uri.parse(image1)).into(binding.img1)
            if (isEditting) {
                binding.btnDelete1.visible()
            } else {
                binding.btnDelete2.gone()
            }
        } else {
            binding.img1.setImageResource(R.drawable.img_empty)
        }
        if (image2.isNotBlank()) {
            Glide.with(this).load(Uri.parse(image2)).into(binding.img2)
            if (isEditting) {
                binding.btnDelete2.visible()
            } else {
                binding.btnDelete2.gone()
            }
        } else {
            binding.img2.setImageResource(R.drawable.img_empty)
        }
        if (image3.isNotBlank()) {
            if (isEditting) {
                binding.btnDelete3.visible()
            } else {
                binding.btnDelete3.gone()
            }
            Glide.with(this).load(Uri.parse(image3)).into(binding.img3)
        } else {
            binding.img3.setImageResource(R.drawable.img_empty)
        }
    }


    private fun enableEdittext() {
        if (!isEditting) {
            binding.edtTitle.isEnabled = false
            binding.edtTitle.isFocusable = false
            binding.edtTitle.isFocusableInTouchMode = false

            binding.edtContent.isEnabled = false
            binding.edtContent.isFocusable = false
            binding.edtContent.isFocusableInTouchMode = false
            closeEditMode()
        } else {
            binding.edtTitle.isEnabled = true
            binding.edtTitle.isFocusable = true
            binding.edtTitle.isFocusableInTouchMode = true

            binding.edtContent.isEnabled = true
            binding.edtContent.isFocusable = true
            binding.edtContent.isFocusableInTouchMode = true
            openEditMode()
        }
    }

    private fun showImage(img: Int, link: String) {
        imgSelect = img
        isShowing = true
        binding.imgShow.visible()
        binding.btnEdit.gone()
        Glide.with(this).load(Uri.parse(link)).into(binding.imgShow)
    }

    private fun unShowImage() {
        isShowing = false
        binding.imgShow.gone()
        binding.btnEdit.visible()
    }

    override fun onBackPressed() {
        if (isShowing) {
            unShowImage()
        } else if (isEditting) {
            isEditting = false
        } else {
            super.onBackPressed()
        }
    }

    private fun openEditMode() {
        isEditting = true
        binding.btnCreate.visible()
        loadImage()
        enableEdittext()
    }

    private fun closeEditMode() {
        isEditting = false
        binding.btnCreate.gone()
        loadImage()
        enableEdittext()
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


    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                diary.emotion = emotion
                diary.title = binding.edtTitle.text.toString()
                diary.imageLink1 = image1
                diary.imageLink2 = image2
                diary.imageLink3 = image3
                diary.content = binding.edtContent.text.toString()
                diaryViewModel.updateDiary(diary)
            }
        }
    }
}