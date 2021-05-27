package com.olyv.olyvadmin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.olyv.olyvadmin.R
import com.olyv.olyvadmin.utils.LoadingDialog
import kotlinx.android.synthetic.main.activity_phone_number_verification.view.*
import kotlinx.android.synthetic.main.activity_phone_number_verification.view.tv_title
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog.view.tv_body
import kotlinx.android.synthetic.main.patient_remove_alert_dialog.view.*

abstract class BaseFragment(layout:Int) : Fragment(layout) {

    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    }

    fun startActivity(cls: Class<*>?) {
        val intent = Intent(context, cls)
        super.startActivity(intent)
    }

    fun showLoadingDialog() {
        if (this::loadingDialog.isInitialized && !loadingDialog.isShowing()) {
            loadingDialog.show()
        }
    }

    fun changeLoadingStatus(show: Boolean) {
        if (show) showLoadingDialog()
        else hideLoadingDialog()
    }

    fun showLoadingDialog(title: String) {
        showLoadingDialog()
    }

    fun hideLoadingDialog() {
        if (this::loadingDialog.isInitialized && loadingDialog.isShowing()) {
            loadingDialog.dismiss()
        }
    }

    fun showCustomDialog(text: String, okay: () -> Unit = {}) {
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        val view = layoutInflater.inflate(R.layout.custom_alert_dialog, null)
        view.tv_body.text = text
        alertDialog.setView(view)
        val dialog = alertDialog.create()
        dialog.show()
        view.tv_okay.setOnClickListener {
            okay()
            dialog.dismiss()
        }
    }

    fun showConfirmationDialog(body: String, yes: () -> Unit = {},title:String="") {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.patient_remove_alert_dialog, null)
        val alertDialog = android.app.AlertDialog.Builder(requireContext())
        alertDialog.setView(view)
        if (title!="")
            view.tv_title.text=title
        view.tv_body.text=body
        val dialog = alertDialog.show()
        view.tv_yes.setOnClickListener {
            yes()
            dialog.dismiss()
        }
        view.tv_no.setOnClickListener {
            dialog.dismiss()
        }
    }

}