package com.diary.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diary.Common.getUserName
import com.diary.Common.setUserName
import com.diary.R
import com.diary.activity.ChooseThemeActivity
import com.diary.activity.DailyReminderSettingActivity
import com.diary.activity.LanguageActivity
import com.diary.databinding.DialogCreateScheduleBinding
import com.diary.databinding.DialogRenameBinding
import com.diary.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private val binding by lazy { FragmentSettingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(requireContext().getUserName().isBlank()){
            binding.tvName.text = getText(R.string.noname)
        }else{
            binding.tvName.text = requireContext().getUserName()
        }


        binding.btnName.setOnClickListener {
            showDialogRename()
        }
        binding.btnTheme.setOnClickListener {
            startActivity(Intent(requireContext(), ChooseThemeActivity::class.java))
        }
        binding.btnPass.setOnClickListener {

        }
        binding.btnReminder.setOnClickListener {
            startActivity(Intent(requireContext(), DailyReminderSettingActivity::class.java))
        }
        binding.btnLanguage.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    LanguageActivity::class.java
                ).putExtra("FROM_SETTING", true)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun showDialogRename() {
        val bindingDialog = DialogRenameBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(bindingDialog.root)
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bindingDialog.edtUsername.setText(requireContext().getUserName())
        bindingDialog.btnSave.setOnClickListener {
            requireContext().setUserName(bindingDialog.edtUsername.text.toString())
            if(requireContext().getUserName().isBlank()){
                binding.tvName.text = getText(R.string.noname)
            }else{
                binding.tvName.text = requireContext().getUserName()
            }
            dialog.dismiss()
        }
        bindingDialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}