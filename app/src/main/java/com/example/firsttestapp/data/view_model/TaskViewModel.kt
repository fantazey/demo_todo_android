package com.example.firsttestapp.data.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.firsttestapp.data.dao.TaskDao
import com.example.firsttestapp.data.model.CheckListItem
import com.example.firsttestapp.data.model.Task
import com.example.firsttestapp.data.model.TaskCheckList
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class TaskViewModel(val dao: TaskDao, application: Application): AndroidViewModel(application) {
    val tasks = dao.getAll()
    val app = application
    private val _navigateToTask = MutableLiveData<Int?>()
    val navigateToTask: LiveData<Int?>
        get() = _navigateToTask

    var checkListItems: LiveData<List<CheckListItem>>? = null
    var task: LiveData<Task>? = null


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
                task.relatedPlace = jsonTask.getString("related_place")
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

    fun getTaskById(taskId: Int): LiveData<Task> {
        return dao.get(taskId)
    }

    fun onTaskSelected(taskId: Int) {
        _navigateToTask.value = taskId
    }

    fun onTaskNavigated() {
        _navigateToTask.value = null
    }

    fun readCheckListItems(taskId: Int) {
        checkListItems = getCheckListItemsByTaskId(taskId)
        task = dao.get(taskId)
    }

    fun loadCheckList(taskId: Int) {
        val volleyQueue = Volley.newRequestQueue(app)
        val url = "http://188.134.89.115:8084/todo_mobile/checklists/?task_id=${taskId}"
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response -> handleCheckListResponse(response) },
            { error -> Toast.makeText(app, error.message, Toast.LENGTH_LONG).show() }
        )
        volleyQueue.add(jsonObjectRequest)
    }

    private fun handleCheckListResponse(response: JSONArray) {
        if (response.length() == 0) {
            return
        }
        val data = response.getJSONObject(0)
        val taskCheckList = TaskCheckList()
        taskCheckList.id = data.getInt("id")
        taskCheckList.taskId = data.getInt("task_id")
        viewModelScope.launch {
            dao.getTaskCheckListById(taskCheckList.id) ?: createTaskCheckList(taskCheckList)
        }

        val items = data.getJSONArray("items")
        viewModelScope.launch {
            val gson = Gson()
            for (i in 0..<items.length()) {
                val jsonCheckListItem = items.getJSONObject(i)
                val checkListItem = gson.fromJson(jsonCheckListItem.toString(), CheckListItem::class.java)
                checkListItem.checkListItemId = taskCheckList.id
                val tmp = dao.getCheckListItemById(checkListItem.id)
                if (tmp == null) {
                    createCheckListItem(checkListItem)
                }
//                dao.getCheckListItemById(checkListItem.id) ?: createCheckListItem(checkListItem)
            }
        }
    }

    private suspend fun createTaskCheckList(record: TaskCheckList) {
        dao.insertTaskCheckList(record)
    }

    private suspend fun createCheckListItem(record: CheckListItem) {
        dao.insertCheckListItem(record)
    }

    private fun getCheckListItemsByTaskId(taskId: Int): LiveData<List<CheckListItem>> {
        return dao.getCheckListItems(taskId)
    }

    fun saveCheckListItemCount(id: Int, count: Int) {
        viewModelScope.launch {
            val item = dao.getCheckListItemById(id)
            item?.let {
                item.count = count
                dao.updateCheckListItem(item)
            }
        }
    }

}