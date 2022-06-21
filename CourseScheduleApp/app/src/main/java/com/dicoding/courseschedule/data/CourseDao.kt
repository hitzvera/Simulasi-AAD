package com.dicoding.courseschedule.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import java.util.*

//TODO 2 : Define data access object (DAO)
@Dao
interface CourseDao {

    @RawQuery(observedEntities = [Course::class])
    fun getNearestSchedule(query: SupportSQLiteQuery): LiveData<Course?>

    @RawQuery(observedEntities = [Course::class])
    fun getAll(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>

    @Query("SELECT * FROM course WHERE id=:id")
    fun getCourse(id: Int): LiveData<Course>

    @Query("""
      SELECT * FROM course 
         WHERE day = (strftime('%w', 'now', 'localtime') + 1)
         AND strftime('%H:%M', startTime) > strftime('%H:%M', 'now', 'localtime')
         ORDER BY strftime('%H:%M', startTime)
    """)
    fun getTodaySchedule(): List<Course>

    @Insert
    fun insert(course: Course)

    @Delete
    fun delete(course: Course)

    @RawQuery(observedEntities = [Course::class])
    fun sort(query: SupportSQLiteQuery): DataSource.Factory<Int, Course>
}