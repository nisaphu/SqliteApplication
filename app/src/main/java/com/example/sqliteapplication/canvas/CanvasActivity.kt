package com.example.sqliteapplication.canvas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import com.example.sqliteapplication.R

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_canvas)
        bindData()
    }

    private fun bindData(){
        val canvasView = MyCanvasView(this)
        canvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        canvasView.contentDescription = getString(R.string.canvas_desc)

        setContentView(canvasView)
    }
}
