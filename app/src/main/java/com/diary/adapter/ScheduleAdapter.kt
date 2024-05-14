package com.diary.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.diary.Common
import com.diary.Common.gone
import com.diary.Common.setReminder
import com.diary.Common.visible
import com.diary.R
import com.diary.database.Schedule
import com.diary.database.ScheduleViewModel
import com.diary.databinding.DialogCreateScheduleBinding
import com.diary.databinding.DialogDeleteImageBinding
import com.diary.databinding.ItemScheduleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ScheduleAdapter(
    private val context: Context,
    private var schedules: MutableList<Schedule>,
    private val viewModel: ScheduleViewModel,
    private val lifecycleScope: LifecycleCoroutineScope
) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemScheduleBinding =
            ItemScheduleBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        holder.onClick(position)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    inner class ViewHolder(val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            val schedule = schedules[pos]
            binding.tvTitle.text = schedule.title.ifBlank {
                context.getText(R.string.notitle)
            }
            binding.tvContent.text = schedule.content.ifBlank {
                context.getText(R.string.nocontent)
            }
            binding.tvTime.text = schedule.time
            if (schedule.isReminder) {
                binding.btnReminder.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#FF0000"))
            } else {
                binding.btnReminder.imageTintList =
                    ColorStateList.valueOf(Color.parseColor("#80000000"))
            }
            if (pos == schedules.size - 1) {
                binding.viewSpace.visible()
            } else {
                binding.viewSpace.gone()
            }
        }

        fun onClick(pos: Int) {
            binding.btnReminder.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val schedule = schedules[pos]
                        schedule.isReminder = !schedule.isReminder
                        viewModel.updateSchedule(schedule)
                    }
                }
            }

            binding.bgItem.setOnClickListener {
                val bindingDialog =
                    DialogCreateScheduleBinding.inflate(LayoutInflater.from(context))
                val dialog = Dialog(context)
                dialog.setContentView(bindingDialog.root)
                val window = dialog.window
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.show()

                bindingDialog.title.text = context.getText(R.string.edit_schedule)
                bindingDialog.btnYes.text = context.getText(R.string.confirm)

                val schedule = schedules[pos]
                var isAMM = true

                val timeTriple = Common.convertStringToHour(schedule.time)
                timeTriple?.let { (hour, minute, isAM) ->
                    isAMM = isAM
                    bindingDialog.nbHour.value = hour.toInt()
                    bindingDialog.nbMinute.value = minute.toInt()
                }

                if (isAMM) {
                    bindingDialog.btnAm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#766DE6"))
                    bindingDialog.btnPm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
                    bindingDialog.btnAm.setTextColor(Color.WHITE)
                    bindingDialog.btnPm.setTextColor(Color.parseColor("#383655"))
                } else {
                    bindingDialog.btnPm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#766DE6"))
                    bindingDialog.btnAm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
                    bindingDialog.btnPm.setTextColor(Color.WHITE)
                    bindingDialog.btnAm.setTextColor(Color.parseColor("#383655"))
                }

                bindingDialog.edtTitle.setText(schedule.title)
                bindingDialog.edtContent.setText(schedule.content)
                bindingDialog.swReminder.isChecked = schedule.isReminder

                bindingDialog.btnAm.setOnClickListener {
                    isAMM = true
                    bindingDialog.btnAm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#766DE6"))
                    bindingDialog.btnPm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
                    bindingDialog.btnAm.setTextColor(Color.WHITE)
                    bindingDialog.btnPm.setTextColor(Color.parseColor("#383655"))
                }
                bindingDialog.btnPm.setOnClickListener {
                    isAMM = false
                    bindingDialog.btnPm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#766DE6"))
                    bindingDialog.btnAm.backgroundTintList =
                        ColorStateList.valueOf(Color.parseColor("#00FFFFFF"))
                    bindingDialog.btnPm.setTextColor(Color.WHITE)
                    bindingDialog.btnAm.setTextColor(Color.parseColor("#383655"))
                }

                bindingDialog.btnCancel.setOnClickListener {
                    dialog.dismiss()
                }

                bindingDialog.btnYes.setOnClickListener {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            val a = if (isAMM) {
                                "AM"
                            } else {
                                "PM"
                            }
                            schedule.title = bindingDialog.edtTitle.text.toString()
                            schedule.content = bindingDialog.edtContent.text.toString()
                            schedule.time =
                                (bindingDialog.nbHour.value - 1).toString() + ":" + bindingDialog.nbMinute.value.toString() + " " + a
                            schedule.isReminder = bindingDialog.swReminder.isChecked
                            viewModel.updateSchedule(schedule)
                            dialog.dismiss()
                        }
                    }
                }

            }

            binding.bgItem.setOnLongClickListener {
                val bindingDialog = DialogDeleteImageBinding.inflate(LayoutInflater.from(context))
                val dialog = Dialog(context)
                dialog.setContentView(bindingDialog.root)
                val window = dialog.window
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.show()

                bindingDialog.tvTitle.text = context.getText(R.string.delete_schedule)

                bindingDialog.btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
                bindingDialog.btnYes.setOnClickListener {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            viewModel.deleteSchedule(schedules[pos])
                            dialog.dismiss()
                        }
                    }
                }
                true
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeList(listschedule: MutableList<Schedule>) {
        schedules = listschedule
        notifyDataSetChanged()
    }
}

