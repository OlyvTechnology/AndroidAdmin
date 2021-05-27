package com.olyv.olyvadmin.ui.dashboard.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.olyv.olyvadmin.R
import com.olyv.olyvadmin.base.BaseActivity
import com.olyv.olyvadmin.model.UserDataByAdmin
import com.olyv.olyvadmin.ui.dashboard.adaptor.UserAdaptor
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var firestore: FirebaseFirestore
    val list = ArrayList<UserDataByAdmin>()
    lateinit var adaptor: UserAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        getData()
        clickListeners()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun clickListeners() {
        iv_add.setOnClickListener {
            startActivity(AddUserActivity::class.java)
        }
    }

    fun initView() {
        firestore = FirebaseFirestore.getInstance()
        adaptor = UserAdaptor(list,toggleRight)
        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adaptor
    }

    private fun getData() {
        firestore
            .collection("USER_BY_ADMIN")
            .get()
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    showToast(it.exception!!.message!!)
                    return@addOnCompleteListener
                }
                val data = it.result!!.toObjects(UserDataByAdmin::class.java)
                list.clear()
                list.addAll(data)
                adaptor.notifyDataSetChanged()
            }
    }

    val toggleRight = fun(uuid: String,allowed:Boolean) {
        showLoadingDialog()
        firestore
            .collection("USER_BY_ADMIN")
            .document(uuid)
            .update("allowed",allowed)
            .addOnCompleteListener {
                hideLoadingDialog()
                if (!it.isSuccessful) {
                    showToast(it.exception!!.message!!)
                    return@addOnCompleteListener
                }
            }
    }
}