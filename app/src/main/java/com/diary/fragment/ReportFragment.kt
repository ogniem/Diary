package com.diary.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.diary.Common.convertStringToCalendar
import com.diary.Common.getDay
import com.diary.Common.getMonth
import com.diary.CustomFillFormatter
import com.diary.R
import com.diary.database.DiaryDatabase
import com.diary.databinding.FragmentReportBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportFragment : Fragment() {

    private val binding by lazy { FragmentReportBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lineChart.description.text = ""

        binding.lineChart.axisLeft.setDrawLabels(false)
        binding.lineChart.axisLeft.setDrawGridLines(false)
        binding.lineChart.axisRight.setDrawLabels(false)
        binding.lineChart.axisRight.setDrawGridLines(false)

        binding.lineChart.xAxis.granularity = 1f
        binding.lineChart.axisLeft.granularity = 1f

        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.lineChart.xAxis.setDrawAxisLine(false)
        binding.lineChart.xAxis.gridColor = Color.parseColor("#A3A3A3")
        binding.lineChart.xAxis.gridLineWidth = 1f
        binding.lineChart.xAxis.typeface =
            ResourcesCompat.getFont(requireContext(), R.font.nunito_semibold)
        binding.lineChart.xAxis.textColor = Color.parseColor("#383655")
        binding.lineChart.xAxis.textSize = 12f

        val minValue = -0.5f // Giá trị tối thiểu
        val maxValue = 6.5f // Giá trị tối đa

        binding.lineChart.axisLeft.axisMinimum = minValue
        binding.lineChart.axisLeft.axisMaximum = maxValue
        binding.lineChart.isScaleYEnabled = false
        binding.lineChart.invalidate()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        TransitionManager.beginDelayedTransition(binding.root)
        val entries = ArrayList<Entry>()
        val listTime = ArrayList<String>()
        var count_emo_1 = 0
        var count_emo_2 = 0
        var count_emo_3 = 0
        var count_emo_4 = 0
        var count_emo_5 = 0
        var count_emo_6 = 0
        var count_emo_7 = 0
        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                val database = DiaryDatabase.getInstance(requireContext())
                val dao = database?.diaryEntryDao()
                val listDiary = dao?.allDiaryEntries

                listDiary
            }
            withContext(Dispatchers.Main) {
                result?.let{listDiary->
                    if (!listDiary.isNullOrEmpty()) {
                        val list = listDiary.reversed()
                        for ((index, diary) in list.withIndex()) {
                            entries.add(Entry(index.toFloat(), diary!!.emotion.toFloat()))
                            val time = diary.timeCreate.convertStringToCalendar()
                            listTime.add(time.getDay().toString() + "/" + time.getMonth())

                            when (diary.emotion) {
                                0 -> count_emo_1++
                                1 -> count_emo_2++
                                2 -> count_emo_3++
                                3 -> count_emo_4++
                                4 -> count_emo_5++
                                5 -> count_emo_6++
                                6 -> count_emo_7++
                            }
                        }
                        binding.circularProgressBar1.setProgress((count_emo_1 * 100 / listDiary.size))
                        binding.circularProgressBar2.setProgress((count_emo_2 * 100 / listDiary.size))
                        binding.circularProgressBar3.setProgress((count_emo_3 * 100 / listDiary.size))
                        binding.circularProgressBar4.setProgress((count_emo_4 * 100 / listDiary.size))
                        binding.circularProgressBar5.setProgress((count_emo_5 * 100 / listDiary.size))
                        binding.circularProgressBar6.setProgress((count_emo_6 * 100 / listDiary.size))
                        binding.circularProgressBar7.setProgress((count_emo_7 * 100 / listDiary.size))

                        binding.tv1.text = "${count_emo_1}/${(count_emo_1 * 100 / listDiary.size)}%"
                        binding.tv2.text = "${count_emo_2}/${(count_emo_2 * 100 / listDiary.size)}%"
                        binding.tv3.text = "${count_emo_3}/${(count_emo_3 * 100 / listDiary.size)}%"
                        binding.tv4.text = "${count_emo_4}/${(count_emo_4 * 100 / listDiary.size)}%"
                        binding.tv5.text = "${count_emo_5}/${(count_emo_5 * 100 / listDiary.size)}%"
                        binding.tv6.text = "${count_emo_6}/${(count_emo_6 * 100 / listDiary.size)}%"
                        binding.tv7.text = "${count_emo_7}/${(count_emo_7 * 100 / listDiary.size)}%"
                    }
                    val dataSet = LineDataSet(entries, "")

                    dataSet.color = Color.parseColor("#766DE6")
                    dataSet.setDrawValues(false)
                    dataSet.formSize = 0f
                    dataSet.lineWidth = 1.5f
                    dataSet.setCircleColor(Color.parseColor("#766DE6")) // Màu của các nút
                    dataSet.circleRadius = 5f
                    dataSet.setDrawCircleHole(false)
                    val gradientColors = intArrayOf(
                        Color.parseColor("#6C70CA"),
                        Color.parseColor("#006C70CA")
                    ) // Màu của gradient
                    dataSet.fillDrawable = GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        gradientColors
                    ) // Sử dụng GradientDrawable làm fillColor
                    dataSet.fillAlpha = 100 // Độ đậm của màu fill
                    dataSet.setDrawFilled(true) // Kích hoạt chức năng fill
                    dataSet.fillFormatter = CustomFillFormatter()

                    // Thiết lập nhãn cho trục x
                    binding.lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(listTime)
                    val lineData = LineData(dataSet)
                    binding.lineChart.data = lineData
                    binding.lineChart.invalidate()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}