package com.example.firsttestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.firsttestapp.data.model.CheckListItem
import com.example.firsttestapp.databinding.CheckListItemBinding

class CheckListItemAdapter(val clickListener: (taskId: Int, count: Int) -> Unit): RecyclerView.Adapter<CheckListItemAdapter.CheckListItemHolder>() {
    var data = listOf<CheckListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CheckListItemHolder(val binding: CheckListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): CheckListItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CheckListItemBinding.inflate(layoutInflater, parent, false)
                return CheckListItemHolder(binding)
            }
        }

        fun bind(item: CheckListItem, clickListener: (itemId: Int, count: Int) -> Unit) {
            binding.item = item
            binding.root.findViewById<Button>(R.id.check_listitem_save_btn).setOnClickListener {
                clickListener(item.id, Integer.parseInt(binding.checkListitemCount.text.toString()))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListItemHolder =
        CheckListItemHolder.inflateFrom(parent)

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CheckListItemHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }
}