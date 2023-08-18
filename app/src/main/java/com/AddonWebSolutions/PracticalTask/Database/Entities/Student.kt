package com.AddonWebSolutions.PracticalTask.Database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 *Created By Mayank
 **/

@Entity(tableName = "Student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val student_id: Int? = null,
    @ColumnInfo(name = "student_name") val student_name: String,
    @ColumnInfo(name = "student_gender") val student_gender: Int,
    @ColumnInfo(name = "student_image") val student_image: String,
)