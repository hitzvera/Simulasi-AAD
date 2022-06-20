package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.databinding.ActivityTaskDetailBinding
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.ui.list.TaskAdapter
import com.dicoding.todoapp.utils.*

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel
    private var id = 0
    private var dueDate: Long = 0
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var binding: ActivityTaskDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 11 : Show detail task and implement delete action
        id = intent.getIntExtra(TASK_ID, 0)
        title = intent.getStringExtra(TASK_TITLE).toString()
        description = intent.getStringExtra(TASK_DESCRIPTION).toString()
        dueDate = intent.getLongExtra(TASK_DUE_DATE, 0)

        bindData()

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]
        viewModel.setTaskId(id)
        val btnDeleteTask: Button = findViewById(R.id.btn_delete_task)
        btnDeleteTask.setOnClickListener {
            deleteUser()
        }
    }

    private fun bindData(){
        binding.detailEdTitle.setText(title)
        binding.detailEdDescription.setText(description)
        binding.detailEdDueDate.setText(DateConverter.convertMillisToString(dueDate))
    }

    private fun deleteUser(){
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") {_, _ ->
            viewModel.task.observe(this){
                viewModel.deleteTask()
            }
            finish()
        }
        builder.setNegativeButton("No"){_,_,->}
        builder.setTitle("Delete Task")
        builder.setMessage("Are you sure want to delete this task?")
        builder.create().show()
    }
}