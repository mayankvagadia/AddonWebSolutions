package com.AddonWebSolutions.PracticalTask.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.AddonWebSolutions.PracticalTask.Database.Dao.StudentDao
import com.AddonWebSolutions.PracticalTask.Database.Entities.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/***
 *Created By Mayank
 **/

@Database(entities = [Student::class], version = 1)
abstract class StudentRoomDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
        ): StudentRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentRoomDatabase::class.java,
                    "student_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope,
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                    }
                }
            }
        }
    }
}