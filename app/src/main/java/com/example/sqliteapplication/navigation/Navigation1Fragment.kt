package com.example.sqliteapplication.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

import com.example.sqliteapplication.R
import kotlinx.android.synthetic.main.fragment_navigation1.*

class Navigation1Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            it.findNavController().navigate(
                Navigation1FragmentDirections
                    .actionNavigation1FragmentToNavigation2Fragment("mobile", 5, "test")
            )
        }
    }

}
