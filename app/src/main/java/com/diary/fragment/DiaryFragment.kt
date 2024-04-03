package com.diary.fragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.diary.adapter.DiaryAdapter
import com.diary.databinding.FragmentDiaryBinding
import java.util.Date


class DiaryFragment : Fragment() {
    private val binding by lazy { FragmentDiaryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var dates: MutableList<Date> = ArrayList()

        val date = Date()

        dates.add(date)

        dates.add(date)

        dates.add(date)
        dates.add(date)
        dates.add(date)
        dates.add(date)
        dates.add(date)

        Log.d("TAG123", "onCreateView: "+dates.size)

        val adapter = DiaryAdapter(dates)

        binding.rcvDiary.setAdapter(adapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}