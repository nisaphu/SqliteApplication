package com.example.sqliteapplication.speech

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*

class BaseTTS(context: Context) : UtteranceProgressListener(), TextToSpeech.OnInitListener,
    TextToSpeech.OnUtteranceCompletedListener {
    var baseTTS: BaseTTS? = null

    fun getInstance(context: Context): BaseTTS? {
        if (baseTTS == null) {
            baseTTS = BaseTTS(context)
        }
        return baseTTS
    }

    private var context: Context = context
    private var tts: TextToSpeech? = null
    private var locale = Locale.getDefault()
    private var enginePackageName: String? = null
    private var message: String? = null
    private var isRunning: Boolean = false
    private var speakCount = 0

    fun speak(message: String) {
        this.message = message

        if (tts == null || !isRunning) {
            speakCount = 0
            if (enginePackageName != null && enginePackageName!!.isNotEmpty()) {
                tts = TextToSpeech(context, this, enginePackageName)
            } else {
                tts = TextToSpeech(context, this)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                tts?.setOnUtteranceProgressListener(this)
            } else {
                tts?.setOnUtteranceCompletedListener(this)
            }

            isRunning = true
        } else {
            startSpeak()
        }
    }

    fun setEngine(packageName: String): BaseTTS {
        enginePackageName = packageName
        return this
    }

    fun setLocal(locale: Locale): BaseTTS {
        this.locale = locale
        return this
    }

    private fun startSpeak() {
        speakCount++

        if (locale != null) {
            tts?.language = locale
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    private fun clear() {
        speakCount--
        if (speakCount == 0) {
            tts?.shutdown()
            isRunning = false
        }
    }

    override fun onDone(utteranceId: String?) {
        clear()
    }

    override fun onError(utteranceId: String?) {
        clear()
    }

    override fun onStart(utteranceId: String?) {
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            startSpeak()
        }
    }

    override fun onUtteranceCompleted(utteranceId: String?) {
        clear()
    }
}