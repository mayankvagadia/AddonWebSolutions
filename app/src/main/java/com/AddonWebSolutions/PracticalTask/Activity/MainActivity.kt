package com.AddonWebSolutions.PracticalTask.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.AddonWebSolutions.PracticalTask.Adapter.StudentAdapter
import com.AddonWebSolutions.PracticalTask.Database.SqLiteDB
import com.AddonWebSolutions.PracticalTask.Model.Student
import com.AddonWebSolutions.PracticalTask.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var fabAddStudent: FloatingActionButton
    private lateinit var rvStudents: RecyclerView
    lateinit var db: SqLiteDB
    lateinit var students: ArrayList<Student>
    lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddStudent = findViewById(R.id.fab_add_student)
        rvStudents = findViewById(R.id.rv_students)
        db = SqLiteDB(this, null)
        students = ArrayList()
        students = db.getStudent()
        adapter = StudentAdapter(this, students)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = adapter

        fabAddStudent.setOnClickListener {
            startActivity(Intent(this@MainActivity, StudentRegisterActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()


    }
}