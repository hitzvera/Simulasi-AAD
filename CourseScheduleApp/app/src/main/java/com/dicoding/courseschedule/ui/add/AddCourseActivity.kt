package com.dicoding.courseschedule.ui.add

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.ui.home.HomeViewModelRepository
import com.dicoding.courseschedule.ui.setting.SettingsActivity
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.TimePickerFragment

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    private lateinit var binding: ActivityAddCourseBinding
    private var btnChecker = "pending"
    private var startTime: String = "08:00"
    private var endTime: String = "09:00"
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = HomeViewModelRepository.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
        val spinner: Spinner = findViewById(R.id.day_spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                day = position
                Log.e("CHECK", position.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        binding.btnStartTime.setOnClickListener {
            btnChecker = "startTime"
            val timePickerFragment: DialogFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, "timePickerFragment")
        }
        binding.btnEndTime.setOnClickListener {
            btnChecker = "endTime"
            val timePickerFragment: DialogFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, "timePickerFragment")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = binding.tvCourseName.text?.trim().toString()
                val lecturer = binding.tvCourseLecturer.text?.trim().toString()
                val note = binding.tvCourseNote.text?.trim().toString()
                viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        if(btnChecker == "startTime"){
            startTime = if(minute<10 && hour<10) "0$hour:0$minute"
            else if(minute<10) {
                "$hour:0$minute"
            }
            else if(hour<10){
                "$hour:0$minute"
            } else {
                "$hour:$minute"
            }
            binding.tvStartTime.text = startTime
        } else if(btnChecker == "endTime"){
            endTime = if(minute<10 && hour<10) "0$hour:0$minute"
            else if(minute<10) {
                "$hour:0$minute"
            }
            else if(hour<10){
                "$hour:0$minute"
            } else {
                "$hour:$minute"
            }
            binding.tvStartTime.text = endTime
        }
    }
}