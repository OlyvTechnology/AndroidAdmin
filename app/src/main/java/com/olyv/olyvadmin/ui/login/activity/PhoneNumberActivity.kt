package com.olyv.olyvadmin.ui.login.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.olyv.olyvadmin.ui.dashboard.activity.MainActivity
import com.olyv.olyvadmin.R
import com.olyv.olyvadmin.base.BaseActivity
import com.olyv.olyvadmin.utils.SharedPref
import kotlinx.android.synthetic.main.activity_phone_number_verification.*
import java.util.concurrent.TimeUnit

class PhoneNumberVerificationActivity : BaseActivity() {

    var mAuth: FirebaseAuth? = null
    var mobileNumber: String = ""
    var verificationID: String = ""
    var token_: String = ""
    var PhoneNumber=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number_verification)

        mAuth = FirebaseAuth.getInstance()

        if (FirebaseAuth.getInstance().uid!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        tv_policy.setOnClickListener {
            val myDialog = androidx.appcompat.app.AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.alert_dialog_privacy, null)
            val webView = view.findViewById<WebView>(R.id.m_webview)
            webView.loadUrl("file:///android_asset/privacy.html")
            myDialog.setView(view)
            val alert = myDialog.show()
        }
        tv_term.setOnClickListener {
            val myDialog = androidx.appcompat.app.AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.alert_dialog_privacy, null)
            val webView = view.findViewById<WebView>(R.id.m_webview)
            webView.loadUrl("file:///android_asset/term.html")
            myDialog.setView(view)
            val alert = myDialog.show()
        }

        btn_signin.setOnClickListener {

            mobileNumber = et_number.text.toString()

            if(et_number.text.isEmpty()){
                et_number.error = "Enter number please"
            }else{
                if (mobileNumber.length == 10||mobileNumber.length == 13) {

                    if(mobileNumber.length ==10){
                        mobileNumber="+91"+mobileNumber
                    }
                    PhoneNumber=mobileNumber
                    showLoadingDialog()
                    if(PhoneNumber!="7895720597"){
                        showToast("Enter admin number")
                        return@setOnClickListener
                    }
                    loginTask()

                } else {
                    et_number.setError("Enter valid phone number")
                }
            }
        }
    }

    private fun loginTask() {

        val mCallBacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showLoadingDialog()
                toast(p0.message.toString())
            }


            @SuppressLint("SetTextI18n")
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)
                hideLoadingDialog()
                verificationID = verificationId
                token_ = token.toString()

                et_number.setText("")

                et_number.hint = "Enter OTP "
                tv_title.text="Enter 6 digit code we sent to $PhoneNumber"

                btn_signin.setOnClickListener {
                    showLoadingDialog()
                    verifyAuthentication(verificationID, et_number.text.toString())
                }

                Log.e("Login : verificationId ", verificationId)
                Log.e("Login : token ", token_)

            }

            override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                super.onCodeAutoRetrievalTimeOut(verificationId)
                showLoadingDialog()
                 toast("Time out")
            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mobileNumber,            // Phone number to verify
            60,                  // Timeout duration
            TimeUnit.SECONDS,        // Unit of timeout
            this,                // Activity (for callback binding)
            mCallBacks)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                hideLoadingDialog()
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val sharedPref= SharedPref(this)
                    sharedPref.phone = mobileNumber
                    sharedPref.uuid = user!!.uid
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        showLoadingDialog()
                        toast(getString(R.string.invalid_otp))
                        Log.e("alhaj",task.exception!!.message!!)
                    }
                }
            }
    }

    private fun verifyAuthentication(verificationID: String, otpText: String) {
        try {
            val phoneAuthCredential = PhoneAuthProvider.getCredential(verificationID, otpText)
            signInWithPhoneAuthCredential(phoneAuthCredential)
        }catch (e:Exception){
            toast(getString(R.string.check_internet))
            finish()
        }
    }

    fun toast(s:String){
        Toast.makeText(baseContext,s, Toast.LENGTH_SHORT).show()
    }
}