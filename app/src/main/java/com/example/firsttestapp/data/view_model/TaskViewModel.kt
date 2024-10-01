package com.example.firsttestapp.data.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.firsttestapp.data.dao.TaskDao
import com.example.firsttestapp.data.model.Task
import com.google.gson.Gson
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class TaskViewModel(val dao: TaskDao, application: Application): AndroidViewModel(application) {
    val tasks = dao.getAll()
    val app = application

//    fun fillTasks() {
//        viewModelScope.launch {
//            val count = dao.count()
//            if (count < 40) {
//                for (i in 1..50) {
//                    val task = Task(
//                        i,
//                        String.format(Locale.ENGLISH, "Задача %d", i),
//                        "test-test",
//                        "address sting",
//                        1000 + i * 20
//                    )
//                    dao.insert(task)
//                }
//            }
//        }
//    }

    fun loadTasks() {
        val volleyQueue = Volley.newRequestQueue(app)
        val url = "http://188.134.89.115:8084/todo_mobile/tasks/"
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response -> handleLoadTasksResponse(response) },
            { error -> Toast.makeText(app, error.message, Toast.LENGTH_LONG).show() }
        )
        volleyQueue.add(jsonObjectRequest)
    }

    private fun handleLoadTasksResponse(response: JSONArray) {
        viewModelScope.launch {
            val gson = Gson()
            for (i in 0..<response.length()) {
                val jsonTask = response.get(i) as JSONObject
                val task = gson.fromJson(jsonTask.toString(), Task::class.java)
                task.taskId = jsonTask.getInt("id")
                dao.getById(task.taskId)?.let {
                    updateTask(task)
                } ?: createTask(task)
            }
        }
    }

    private suspend fun createTask(task: Task) {
        dao.insert(task)
    }

    private suspend fun updateTask(task: Task) {
        dao.update(task)
    }
}