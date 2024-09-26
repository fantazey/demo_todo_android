package com.example.firsttestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController


class HomeMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_menu, container, false)
        val username = HomeMenuFragmentArgs.fromBundle(requireArguments()).username
        val usernameLabel = view.findViewById<TextView>(R.id.home_menu_username)
        usernameLabel.text = username
        val tasksButton = view.findViewById<Button>(R.id.home_menu_tasks_button)
        tasksButton.setOnClickListener {
            val action = HomeMenuFragmentDirections.actionHomeMenuFragmentToTasksFragment(username)
            view.findNavController().navigate(action)
        }
        return view
    }
}