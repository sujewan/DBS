package com.sujewan.dbs.view.ui.general

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.snackbar.Snackbar
import com.sujewan.dbs.R
import com.sujewan.dbs.databinding.CustomLayoutSuccessBinding
import com.sujewan.dbs.databinding.CustomLayoutWarningBinding

abstract class BaseAppCompatActivity: AppCompatActivity() {
    private var loadingDialog: MaterialDialog? = null

    protected open fun showSnackBar(view: View, message: String?) {
        val snackBar = Snackbar.make(view, message!!, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Dismiss") {
            snackBar.dismiss()
        }.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.color_red)).show()
    }

    protected fun showSuccessDialog(message: String) {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }

        val binding = CustomLayoutSuccessBinding.inflate(layoutInflater)
        loadingDialog = MaterialDialog(this)
            .customView(view = binding.root)
            .cornerRadius(8f)

        binding.lblSuccessDialog.text = message
        loadingDialog!!.cancelable(false)
        loadingDialog!!.cancelOnTouchOutside(false)
        loadingDialog!!.show()
    }

    protected fun showWarningDialog(warningMessage: String) {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }

        runOnUiThread {
            val binding = CustomLayoutWarningBinding.inflate(layoutInflater)
            loadingDialog = MaterialDialog(this)
                .customView(view = binding.root)
                .cornerRadius(8f)
                .positiveButton(R.string.ok) {}

            binding.lblWarningDialog.text = warningMessage
            loadingDialog!!.cancelable(false)
            loadingDialog!!.cancelOnTouchOutside(false)
            loadingDialog!!.show()
        }
    }
}
