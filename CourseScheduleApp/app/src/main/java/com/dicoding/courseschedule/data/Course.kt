package com.dicoding.courseschedule.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO 1 : Define a local database table using the schema in app/schema/course.json
@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val courseName: String,
    val day: Int,
    val startTime: String,
    val endTime: String,
    val lecturer: String,
    val note: String
)
