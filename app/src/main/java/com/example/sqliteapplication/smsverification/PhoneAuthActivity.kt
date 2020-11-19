package com.example.sqliteapplication.smsverification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sqliteapplication.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_phone_auth.*
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    val TIME_OUT = 60
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var verificationCode = ""
    val TAG = "PoneAuthActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
        defineUi()
//        val credential = PhoneAuthProvider.getCredential()
    }

    private fun defineUi(){
        textView
        textView3
        textView4
        textInputLayout
        outlined_exposed_dropdown_editable
        button2
        textInputET
        editText
        button
        logout
        barrier
    }

    private fun defineFirebase(){
        firebaseAuth = FirebaseAuth.getInstance()
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Toast.makeText(this@PhoneAuthActivity,
                    "verification completed", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@PhoneAuthActivity,
                "verification failed", Toast.LENGTH_SHORT).show()
                Log.d(TAG , "FirebaseException =  ${p0.toString()}")
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationCode = p0
                Log.d(TAG, "verificationCode = $verificationCode")
                Toast.makeText(this@PhoneAuthActivity, "Code Sent", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun verifyPhoneNumberWithCode(phoneNumber: String){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, 60,
            TimeUnit.SECONDS,
            this,
            mCallback
        )
    }
}