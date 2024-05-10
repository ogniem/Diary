package com.diary.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diary.Common
import com.diary.Common.getThemeHome
import com.diary.Common.getUserName
import com.diary.R
import com.diary.adapter.DiaryAdapter
import com.diary.database.DiaryDatabase
import com.diary.database.DiaryRepository
import com.diary.database.DiaryViewModel
import com.diary.databinding.FragmentDiaryBinding


class DiaryFragment : Fragment() {
    private val binding by lazy { FragmentDiaryBinding.inflate(layoutInflater) }
    private lateinit var diaryViewModel: DiaryViewModel
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val diaryEntryDao = DiaryDatabase.getInstance(requireContext()).diaryEntryDao()
        val diaryRepository = DiaryRepository(diaryEntryDao)
        diaryViewModel = DiaryViewModel(diaryRepository)
        diaryViewModel.getAllDiary().observe(this){
            val listDayHasDiary = Common.getListDayHasDiary(it)
            val adapter = DiaryAdapter(requireContext(), listDayHasDiary, it)

            binding.rcvDiary.setAdapter(adapter)
        }

        if (requireContext().getUserName().isNotBlank()) {
            binding.tvHello.text = getString(R.string.hello) + " " + requireContext().getUserName()
        } else {
            binding.tvHello.text = getString(R.string.hello_noname)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        when (requireContext().getThemeHome()) {
            1 -> binding.imgBgTitle.setImageResource(R.drawable.im_home_1)
            2 -> binding.imgBgTitle.setImageResource(R.drawable.im_home_2)
            3 -> binding.imgBgTitle.setImageResource(R.drawable.im_home_3)
            4 -> binding.imgBgTitle.setImageResource(R.drawable.im_home_4)
        }
    }

}