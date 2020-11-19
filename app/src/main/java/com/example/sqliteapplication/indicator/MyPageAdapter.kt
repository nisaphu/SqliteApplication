package com.example.sqliteapplication.indicator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPageAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return OneFragment()
            1 -> return TwoFragment()
            else -> return ThreeFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

}