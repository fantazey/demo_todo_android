package com.example.firsttestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.firsttestapp.data.model.Task
import com.example.firsttestapp.databinding.TaskItemBinding

class TaskItemAdapter(val clickListener: (taskId: Int) -> Unit): RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {
    var data = listOf<Task>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class TaskItemViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }

        fun bind(item: Task, clickListener: (taskId: Int) -> Unit) {
            binding.task = item
            binding.root.findViewById<Button>(R.id.task_item_select_btn).setOnClickListener {
                clickListener(item.taskId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder =
        TaskItemViewHolder.inflateFrom(parent)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }
}