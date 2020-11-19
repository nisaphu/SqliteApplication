package com.example.sqliteapplication.video

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqliteapplication.R
import kotlinx.android.synthetic.main.activity_video_view.*
import android.net.Uri
import android.os.Build
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class VideoViewActivity : AppCompatActivity() {

    companion object {
        private const val PLAYBACK_TIME = "play_time"
        private const val VIDEO_SAMPLE =
            "https://developers.google.com/training/images/tacoma_narrows.mp4"
    }

    private var mCurrentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        val controller = MediaController(this)
        controller.setMediaPlayer(videoView)
    }

    private fun getMedia(mediaName: String): Uri {
        return if (URLUtil.isValidUrl(mediaName)) {
            Uri.parse(mediaName)
        } else {
            Uri.parse("android.resource://$packageName/raw/$mediaName")
        }
    }

    private fun initializePlayer() {
        bufferingTv.visibility = VideoView.VISIBLE
        val videoUri = getMedia(VIDEO_SAMPLE)
        videoView.setVideoURI(videoUri)
        if (mCurrentPosition > 0) {
            videoView.seekTo(mCurrentPosition)
        } else {
            videoView.seekTo(1)
        }

        videoView.setOnPreparedListener {
            bufferingTv.visibility = VideoView.INVISIBLE
            if (mCurrentPosition > 0) {
                videoView.seekTo(mCurrentPosition)
            } else {
                videoView.seekTo(1)
            }
            videoView.start()
        }

        videoView.setOnCompletionListener {
            Toast.makeText(this@VideoViewActivity, "Playback completed", Toast.LENGTH_SHORT).show()
            videoView.seekTo(1)
        }
    }

    private fun releasePlayer() {
        videoView.stopPlayback()
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoView.pause()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PLAYBACK_TIME, videoView.currentPosition)
    }
}
