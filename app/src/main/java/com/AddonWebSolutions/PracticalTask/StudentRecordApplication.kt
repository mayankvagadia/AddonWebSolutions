package com.AddonWebSolutions.PracticalTask

import android.app.Application
import com.AddonWebSolutions.PracticalTask.Database.StudentRoomDatabase
import com.AddonWebSolutions.PracticalTask.Repository.StudentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/***
 *Created By Mayank
 **/

class StudentRecordApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { StudentRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { StudentRepository(database.studentDao()) }
}