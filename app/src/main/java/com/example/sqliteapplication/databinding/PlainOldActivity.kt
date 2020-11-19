package com.example.sqliteapplication.databinding

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.sqliteapplication.R
import kotlinx.android.synthetic.main.activity_plain_old.*

class PlainOldActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(SampleViewModel::class.java) }
    private lateinit var binding: ActivityPlainOldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_plain_old)
        viewModel.name = "Nisa"
        viewModel.lastName = "Wong"
        binding.data = viewModel
//        updateName()
        updateLikes()
    }

    fun onLike(view: View) {
        viewModel.onLike()
        updateLikes()
    }

    private fun updateLikes() {
        //likes.text = viewModel.likes.toString()
        progressBar.progress = (viewModel.likes * 100 / 5).coerceAtMost(100)
        val color = getAssociatedColor(viewModel.popularity, this)
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color))
        imageView.setImageDrawable(getDrawablePopularity(viewModel.popularity, this))
    }

    private fun updateName() {
        plain_name.text = viewModel.name
        plain_lastname.text = viewModel.lastName
    }

    private fun getAssociatedColor(popularity: Popularity, context: Context): Int {
        return when (popularity) {
            Popularity.NORMAL -> context.theme.obtainStyledAttributes(
                intArrayOf(android.R.attr.colorForeground)
            ).getColor(0, 0x000000)
            Popularity.POPULAR -> ContextCompat.getColor(context, R.color.colorPrimary)
            Popularity.STAR -> ContextCompat.getColor(context, R.color.colorPrimaryDark)
        }
    }

    private fun getDrawablePopularity(popularity: Popularity, context: Context): Drawable? {
        return when (popularity) {
            Popularity.NORMAL -> {
                ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
            }
            Popularity.POPULAR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            }
            Popularity.STAR -> {
                ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            }
        }
    }
}
