package com.diary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diary.Common
import com.diary.Common.getThemeHome
import com.diary.Common.getUserName
import com.diary.R
import com.diary.adapter.DiaryAdapter
import com.diary.database.DiaryDatabase
import com.diary.databinding.FragmentDiaryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DiaryFragment : Fragment() {
    private val binding by lazy { FragmentDiaryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!requireContext().getUserName().isBlank()) {
            binding.tvHello.text = getString(R.string.hello) + " " + requireContext().getUserName()
        } else {
            binding.tvHello.text = getString(R.string.hello_noname)
        }


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val database = DiaryDatabase.getInstance(requireContext())
                val dao = database?.diaryEntryDao()
                val listDiary = dao?.allDiaryEntries

                val listDayHasDiary = Common.getListDayHasDiary(listDiary)
                val adapter = DiaryAdapter(requireContext(), listDayHasDiary, listDiary)

                binding.rcvDiary.setAdapter(adapter)
            }
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