package com.dicoding.todoapp.ui.list

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.databinding.TaskItemBinding
import com.dicoding.todoapp.ui.detail.DetailTaskActivity
import com.dicoding.todoapp.utils.*

class TaskAdapter(
    private val onCheckedChange: (Task, Boolean) -> Unit
) : PagedListAdapter<Task, TaskAdapter.TaskViewHolder>(DIFF_CALLBACK) {

    //TODO 8 : Create and initialize ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false))
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position) as Task
        //TODO 9 : Bind data to ViewHolder (You can run app to check)
        holder.bind(task)
        val taskTitleView = TaskTitleView(holder.itemView.context)
        when {
            //TODO 10 : Display title based on status using TitleTextView
            task.isCompleted -> {
                //DONE
                holder.cbComplete.isChecked = true
                taskTitleView.state = TaskTitleView.DONE
                holder.tvTitle.state = taskTitleView.state
            }
            task.dueDateMillis < System.currentTimeMillis() -> {
                //OVERDUE
                holder.cbComplete.isChecked = false
                taskTitleView.state = TaskTitleView.OVERDUE
                holder.tvTitle.state = taskTitleView.state
            }
            else -> {
                //NORMAL
                holder.cbComplete.isChecked = false
                taskTitleView.state = TaskTitleView.NORMAL
                holder.tvTitle.state = taskTitleView.state
            }
        }
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TaskTitleView = itemView.findViewById(R.id.item_tv_title)
        val cbComplete: CheckBox = itemView.findViewById(R.id.item_checkbox)
        val tvDescription: TextView = itemView.findViewById(R.id.item_tv_description)
        private val tvDueDate: TextView = itemView.findViewById(R.id.item_tv_date)

        lateinit var getTask: Task

        fun bind(task: Task) {
            getTask = task
            tvTitle.text = task.title
            tvDescription.text = task.description
            tvDueDate.text = DateConverter.convertMillisToString(task.dueDateMillis)
            itemView.setOnClickListener {
                val detailIntent = Intent(itemView.context, DetailTaskActivity::class.java)
                detailIntent.putExtra(TASK_ID, task.id)
                detailIntent.putExtra(TASK_TITLE, task.title)
                detailIntent.putExtra(TASK_DESCRIPTION, task.description)
                detailIntent.putExtra(TASK_DUE_DATE, task.dueDateMillis)
                itemView.context.startActivity(detailIntent)
            }
            cbComplete.setOnClickListener {
                onCheckedChange(task, !task.isCompleted)
            }
        }

    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }

    }

}