package com.example.sqliteapplication.indicator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import com.example.sqliteapplication.R
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        initWidget()
    }

    private fun initWidget(){
        val pageAdapter = MyPageAdapter(supportFragmentManager)
        pager.adapter = pageAdapter
        dots_indicator.setViewPager(pager)
    }
}
