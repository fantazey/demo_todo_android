package com.example.firsttestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView

class TasksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val tasksFragment = inflater.inflate(R.layout.fragment_tasks, container, false)
        val username = HomeMenuFragmentArgs.fromBundle(requireArguments()).username
        val userNameLabel = tasksFragment.findViewById<TextView>(R.id.fragment_tasks_username_label)
        userNameLabel.text = username
        // todo: load tasks

        return tasksFragment
    }
}