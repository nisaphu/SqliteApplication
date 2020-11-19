package com.example.sqliteapplication.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.example.sqliteapplication.R
import kotlinx.android.synthetic.main.fragment_navigation2.*

class Navigation2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val args = Navigation2FragmentArgs.fromBundle(arguments!!)
        Log.d("navigation2", "NumCorrect: ${args.type}, NumQuestions: ${args.amount}")
        return inflater.inflate(R.layout.fragment_navigation2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        twoButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation2Fragment_to_navigation3Fragment)
        }
    }

}
