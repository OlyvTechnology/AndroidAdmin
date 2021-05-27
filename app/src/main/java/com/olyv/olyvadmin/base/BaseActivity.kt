package com.olyv.olyvadmin.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.olyv.olyvadmin.R
import com.olyv.olyvadmin.utils.ConnectionLiveData
import com.olyv.olyvadmin.utils.LoadingDialog
import com.olyv.olyvadmin.utils.show
import kotlinx.android.synthetic.main.activity_phone_number_verification.view.*
import kotlinx.android.synthetic.main.activity_phone_number_verification.view.tv_title
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*
import kotlinx.android.synthetic.main.custom_alert_dialog.view.tv_body
import kotlinx.android.synthetic.main.patient_remove_alert_dialog.view.*

abstract class BaseActivity : AppCompatActivity() {

    lateinit var loadingDialog: LoadingDialog
    var isInternetAvailable = false
    var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ConnectionLiveData(this).observe(this, Observer<Boolean> { t ->
            isInternetAvailable = t
            if (!firstTime) {
                val string = if (t) "Internet back" else "Internet Lost"
                Snackbar.make(window.decorView.rootView, string, Snackbar.LENGTH_LONG)
                    .setAction("CLOSE") { }
                    .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
                    .show()
            }
            firstTime = false
        })

        loadingDialog = LoadingDialog(this)
    }

    override fun onResume() {
        super.onResume()
        firstTime = true
    }

    fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    fun startActivity(cls: Class<*>?) {
        val intent = Intent(this, cls)
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
        val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
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

    fun showConfirmationDialog(body: String, yes: () -> Unit = {}, title: String = "") {
        val view = LayoutInflater.from(this).inflate(R.layout.patient_remove_alert_dialog, null)
        val alertDialog = android.app.AlertDialog.Builder(this,R.style.CustomAlertDialog)
        alertDialog.setView(view)
        if (title != "") {
            view.tv_title.show()
            view.tv_title.text = title
        }
        view.tv_body.text = body
        val dialog = alertDialog.show()
        view.tv_yes.setOnClickListener {
            yes()
            dialog.dismiss()
        }
        view.tv_no.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showError(string:String,editText: EditText?=null){
        if (editText!=null) editText.error = string
        showToast(string)
    }

    fun showError(string:Int,editText: EditText?=null){
        showError(getString(string),editText)
    }

}