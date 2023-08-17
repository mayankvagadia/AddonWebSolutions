package com.AddonWebSolutions.PracticalTask.Adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.AddonWebSolutions.PracticalTask.Model.Student
import com.AddonWebSolutions.PracticalTask.R

class StudentAdapter(private val context: Context, private val student: ArrayList<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : ViewHolder(itemView) {
        val txtStudentId: TextView
        val txtStudentName: TextView
        val txtGender: TextView
        val imgDown: ImageView
        val imgStudent: ImageView
        val clExpand: ConstraintLayout

        init {
            txtStudentId = itemView.findViewById(R.id.txt_student_id)
            txtStudentName = itemView.findViewById(R.id.txt_student_name)
            txtGender = itemView.findViewById(R.id.txt_gender)
            imgDown = itemView.findViewById(R.id.img_down)
            imgStudent = itemView.findViewById(R.id.img_student)
            clExpand = itemView.findViewById(R.id.cl_expand)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.txtStudentName.text = student[position].student_name
        holder.txtStudentId.text = "" + student[position].student_id
        if (student[position].student_gender == "1") {
            holder.txtGender.text = "Male"
        } else if (student[position].student_gender == "2") {
            holder.txtGender.text = "Female"
        }
        holder.imgStudent.setImageBitmap(BitmapFactory.decodeFile(student[position].student_image))
        holder.imgDown.setOnClickListener {
            if (!holder.clExpand.isVisible) {
                holder.clExpand.visibility = View.VISIBLE
            } else {
                holder.clExpand.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return student.size
    }
}