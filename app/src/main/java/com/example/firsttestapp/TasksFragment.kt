package com.example.firsttestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.firsttestapp.data.TaskViewModelFactory
import com.example.firsttestapp.data.TodoDatabase
import com.example.firsttestapp.data.view_model.TaskViewModel
import com.example.firsttestapp.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = TodoDatabase.getInstance(application).taskDao
        val viewModelFactory = TaskViewModelFactory(dao, application)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
        viewModel.loadTasks()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val username = HomeMenuFragmentArgs.fromBundle(requireArguments()).username

        val userNameLabel = view.findViewById<TextView>(R.id.fragment_tasks_username_label)
        userNameLabel.text = username
        val clickListener: (Int) -> Unit = { id ->
            viewModel.loadCheckList(id)
            viewModel.onTaskSelected(id)
        }
        val adapter = TaskItemAdapter(clickListener)

        binding.taskList.adapter = adapter
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        val navigate: (id: Int) -> Unit = { id ->
            val action = TasksFragmentDirections.actionTasksFragmentToEditTaskFragment(username = username, taskId = id)
            view.findNavController().navigate(action)
            viewModel.onTaskNavigated()
        }
        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let(navigate)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}