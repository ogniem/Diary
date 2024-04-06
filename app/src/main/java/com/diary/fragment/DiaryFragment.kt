package com.diary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diary.Common
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

}