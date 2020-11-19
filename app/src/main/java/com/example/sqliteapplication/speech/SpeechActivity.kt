package com.example.sqliteapplication.speech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqliteapplication.R
import kotlinx.android.synthetic.main.activity_speech.*

class SpeechActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech)

        speechBtn.setOnClickListener {
            BaseTTS(this).getInstance(this)?.speak("Hello world")
        }
    }
}
