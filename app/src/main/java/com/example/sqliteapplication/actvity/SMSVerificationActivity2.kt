package com.example.sqliteapplication.actvity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sqliteapplication.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.*
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_s_m_s_verification2.*
import timber.log.Timber

class SMSVerificationActivity2 : AppCompatActivity() , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private lateinit var apiClient: GoogleApiClient
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RESOLVE_HINT = 7788
    private val RC_SIGN_IN = 5445

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_m_s_verification2)

        apiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .enableAutoManage(this, null)
            .addApi(Auth.CREDENTIALS_API)
            .build()

        requestLogin()
        initWidget()
    }

    @SuppressLint("LogNotTimber")
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            signOutBtn.visibility = View.VISIBLE
//            requestHint()
            val phone = account.displayName
        } else {
            signOutBtn.visibility = View.GONE
        }
        Log.d("SMSVerificationActivity", "account : $account")
    }

    @SuppressLint("LogNotTimber")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESOLVE_HINT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val credential = data?.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                    Log.d("SMSVerificationActivity", "credential id : ${credential?.id}")
                    retrieverSMS()
                }
                CredentialsApi.ACTIVITY_RESULT_OTHER_ACCOUNT -> {
                    Log.d(
                        "SMSVerificationActivity",
                        "other account : ${CredentialsApi.ACTIVITY_RESULT_OTHER_ACCOUNT}"
                    )
                }
                CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE -> {
                    Log.d(
                        "SMSVerificationActivity",
                        "no hint available : ${CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE}"
                    )
                }
                Activity.RESULT_CANCELED -> {
                    Log.d("SMSVerificationActivity", "result : cancel")
                }
            }
        } else if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun initWidget() {
        sign_in_button.setOnClickListener { requestHint() }
        signOutBtn.setOnClickListener { signOut() }
    }

    private fun requestLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    @SuppressLint("LogNotTimber")
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            signOutBtn.visibility = View.VISIBLE
            Log.w("SMSVerificationActivity", "signInResult: success = $account")
//            requestHint()
        } catch (e: ApiException) {
            Log.w("SMSVerificationActivity", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener {
                signOutBtn.visibility = View.GONE
            }
    }

    @SuppressLint("LogNotTimber")
    private fun requestHint() {
        val hintRequest = HintRequest.Builder()
            .setHintPickerConfig(CredentialPickerConfig.Builder()
                .setShowCancelButton(true)
                .build())
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val intent = Credentials.getClient(this).getHintPickerIntent(hintRequest)
        try {
            startIntentSenderForResult(intent.intentSender, RESOLVE_HINT, null, 0, 0, 0, null)
        }catch (e: IntentSender.SendIntentException){
            Log.d("SMSVerificationActivity", "IntentSender : ${e}")
        }
    }

    private fun retrieverSMS() {
        val client = SmsRetriever.getClient(this)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Timber.tag("SMSVerificationActivity").d("success : success")
        }

        task.addOnFailureListener {
            Timber.tag("SMSVerificationActivity").d("failure : failure")
        }
    }

    override fun onConnected(p0: Bundle?) {
        Timber.tag("SMSVerificationActivity").d("onConnect : failure")
    }

    override fun onConnectionSuspended(p0: Int) {
        Timber.tag("SMSVerificationActivity").d("onConnectionSuspend : failure")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Timber.tag("SMSVerificationActivity").d("onConnectionFail : failure")
    }

}