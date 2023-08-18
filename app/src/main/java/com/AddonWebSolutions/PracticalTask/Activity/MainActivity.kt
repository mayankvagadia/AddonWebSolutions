package com.AddonWebSolutions.PracticalTask.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AddonWebSolutions.PracticalTask.Adapter.StudentAdapter
import com.AddonWebSolutions.PracticalTask.R
import com.AddonWebSolutions.PracticalTask.StudentRecordApplication
import com.AddonWebSolutions.PracticalTask.ViewModel.StudentViewModel
import com.AddonWebSolutions.PracticalTask.ViewModel.StudentViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var fabAddStudent: FloatingActionButton
    private lateinit var rvStudents: RecyclerView
    lateinit var adapter: StudentAdapter

    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory((application as StudentRecordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAddStudent = findViewById(R.id.fab_add_student)
        rvStudents = findViewById(R.id.rv_students)
        adapter = StudentAdapter(this)
        rvStudents.layoutManager = LinearLayoutManager(this)
        rvStudents.adapter = adapter

        fabAddStudent.setOnClickListener {
            startActivity(Intent(this@MainActivity, StudentRegisterActivity::class.java))
        }

        studentViewModel.allStudent.observe(this) {
            adapter.setData(it)
        }
    }
}