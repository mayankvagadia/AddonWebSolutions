package com.AddonWebSolutions.PracticalTask.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.AddonWebSolutions.PracticalTask.Model.Student

class SqLiteDB(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "AddonWebSolutions"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Students"
        const val ID_COL = "student_id"
        const val NAME_COl = "student_name"
        const val GEN_COl = "student_gender"
        const val IMG_COl = "student_image"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COl + " TEXT," +
                GEN_COl + " TEXT," +
                IMG_COl + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(name: String, gen: Int, img: String) {
        val values = ContentValues()
        values.put(NAME_COl, name)
        values.put(GEN_COl, gen)
        values.put(IMG_COl, img)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
//        db.close()
    }

    fun getStudent(): ArrayList<Student> {
        val students: ArrayList<Student> = ArrayList()
        val db = this.readableDatabase
        val student = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        student.moveToFirst()
        while (student.moveToNext()) {
            val st = Student(
                student.getInt(0),
                student.getString(1),
                student.getString(2),
                student.getString(3)
            )
            students.add(st)
        }
        return students
    }

}
