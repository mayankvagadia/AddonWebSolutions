package com.AddonWebSolutions.PracticalTask.Repository

import androidx.annotation.WorkerThread
import com.AddonWebSolutions.PracticalTask.Database.Dao.StudentDao
import com.AddonWebSolutions.PracticalTask.Database.Entities.Student
import kotlinx.coroutines.flow.Flow

/***
 *Created By Mayank
 **/
class StudentRepository(private val studentDao: StudentDao) {

    val allStudent: Flow<List<Student>> = studentDao.getStudentList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertStudent(student: Student) {
        studentDao.addStudent(student)
    }

}
