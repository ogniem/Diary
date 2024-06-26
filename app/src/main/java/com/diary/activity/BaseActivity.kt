package com.diary.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.diary.Common
import com.diary.Common.getPasscode
import com.diary.R
import com.diary.databinding.DialogPassCodeBinding


@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity() {
    private var isShowing = false
    private var dialog: Dialog? = null
    private var bindingDialog: DialogPassCodeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        Common.setLocale(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
        Common.setLocale(this)
    }

    override fun onResume() {
        super.onResume()
        Common.setLocale(this)
    }


    fun showDialog(onCancelDialog: () -> Unit) {
        if (isShowing) {
            return
        }
        isShowing = true
        dialog = Dialog(this)
        bindingDialog = DialogPassCodeBinding.inflate(layoutInflater)

        dialog?.setContentView(bindingDialog!!.getRoot())
        val window = dialog?.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)

        bindingDialog?.btnRecover?.setOnClickListener {
            startActivity(Intent(this, FogortPasscodeActivity::class.java))
        }

        bindingDialog?.btnBackspace?.setOnClickListener {
            editCode(-1)
        }
        bindingDialog?.btnNumber1?.setOnClickListener {
            editCode(1)
        }
        bindingDialog?.btnNumber2?.setOnClickListener {
            editCode(2)
        }
        bindingDialog?.btnNumber3?.setOnClickListener {
            editCode(3)
        }
        bindingDialog?.btnNumber4?.setOnClickListener {
            editCode(4)
        }
        bindingDialog?.btnNumber5?.setOnClickListener {
            editCode(5)
        }
        bindingDialog?.btnNumber6?.setOnClickListener {
            editCode(6)
        }
        bindingDialog?.btnNumber7?.setOnClickListener {
            editCode(7)
        }
        bindingDialog?.btnNumber8?.setOnClickListener {
            editCode(8)
        }
        bindingDialog?.btnNumber9?.setOnClickListener {
            editCode(9)
        }
        bindingDialog?.btnNumber0?.setOnClickListener {
            editCode(0)
        }

        dialog?.setOnDismissListener {
            onCancelDialog.invoke()
            isShowing = false
        }
        dialog?.show()

    }

    private fun editCode(key: Int) {
        if (key != -1) {
            if (bindingDialog?.edtPasscode?.text.toString().length == 4) {
                return
            } else {
                val textCurrent = bindingDialog?.edtPasscode?.text.toString()
                val passCodeConfirm = textCurrent + key
                bindingDialog?.edtPasscode?.setText(passCodeConfirm)
                if (passCodeConfirm.length == 4) {
                    if (passCodeConfirm == getPasscode()) {
                        dialog?.dismiss()
                    } else {
                        bindingDialog?.edtPasscode?.setText("")
                        bindingDialog?.tvStatus?.text = getText(R.string.error_pass)
                        bindingDialog?.tvStatus?.setTextColor(Color.RED)
                    }
                }
            }
        } else {
            if (bindingDialog?.edtPasscode?.text.toString().isBlank()) {
                return
            } else {
                val textCurrent = bindingDialog?.edtPasscode?.text.toString()
                bindingDialog?.edtPasscode?.setText(textCurrent.dropLast(1))
            }
        }
    }
}
