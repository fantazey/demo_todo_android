package com.example.firsttestapp.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firsttestapp.data.dao.TaskDao
import com.example.firsttestapp.data.view_model.TaskViewModel

class TaskViewModelFactory(private val dao: TaskDao, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown viewmodel")
    }
}