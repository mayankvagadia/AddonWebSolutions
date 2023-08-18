package com.AddonWebSolutions.PracticalTask.Database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.AddonWebSolutions.PracticalTask.Database.Entities.Student
import kotlinx.coroutines.flow.Flow

/***
 *Created By Mayank
 **/

@Dao
interface StudentDao {

    @Query("SELECT * FROM Student_table")
    fun getStudentList(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStudent(student: Student)
}