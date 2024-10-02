package com.example.firsttestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firsttestapp.data.TaskViewModelFactory
import com.example.firsttestapp.data.TodoDatabase
import com.example.firsttestapp.data.model.Task
import com.example.firsttestapp.data.view_model.TaskViewModel
import com.example.firsttestapp.databinding.FragmentEditTaskBinding

class EditTaskFragment: Fragment() {
    private var _binding: FragmentEditTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        val fragmentArgs = EditTaskFragmentArgs.fromBundle(requireArguments())
        val username = fragmentArgs.username
        val application = requireNotNull(this.activity).application
        val dao = TodoDatabase.getInstance(application).taskDao
        val viewModelFactory = TaskViewModelFactory(dao, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        viewModel.readCheckListItems(fragmentArgs.taskId)


        val clickListener: (Int, Int) -> Unit = { id, count ->
            viewModel.saveCheckListItemCount(id, count)
        }
        val adapter = CheckListItemAdapter(clickListener)
        binding.checkListItems.adapter = adapter
        viewModel.checkListItems?.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        viewModel.task?.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.task = it
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}