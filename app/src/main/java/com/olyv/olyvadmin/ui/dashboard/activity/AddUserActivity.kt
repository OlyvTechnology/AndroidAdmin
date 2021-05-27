package com.olyv.olyvadmin.ui.dashboard.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.olyv.olyvadmin.R
import com.olyv.olyvadmin.base.BaseActivity
import com.olyv.olyvadmin.model.UserDataByAdmin
import kotlinx.android.synthetic.main.activity_add_user.*
import java.util.*

class AddUserActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        clickListeners()
    }

    private fun clickListeners() {
        iv_back.setOnClickListener {
            finish()
        }
        btn_add.setOnClickListener {
            checkData()
        }
    }

    private fun checkData() {
        if (et_phone.text.isNullOrBlank() || et_phone.text.length != 10){
            showError(R.string.enter_phone_number_below,et_phone)
            return
        }
        addUser()
    }

    private fun addUser() {
        val firestore = FirebaseFirestore.getInstance()
        val uuid = UUID.randomUUID().toString()
        val obj = UserDataByAdmin("+91${et_phone.text}",true,uuid)
        showLoadingDialog()
        firestore.collection("USER_BY_ADMIN").document(uuid).set(obj).addOnCompleteListener {
            hideLoadingDialog()
            if (!it.isSuccessful){
                showToast(it.exception!!.message!!)
                return@addOnCompleteListener
            }
            finish()
        }
    }
}