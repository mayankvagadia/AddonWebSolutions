package com.AddonWebSolutions.PracticalTask.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.AddonWebSolutions.PracticalTask.Database.Entities.Student
import com.AddonWebSolutions.PracticalTask.Repository.StudentRepository
import kotlinx.coroutines.launch

/***
 *Created By Mayank
 **/
class StudentViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    val allStudent: LiveData<List<Student>> = studentRepository.allStudent.asLiveData()

    fun insert(student: Student) = viewModelScope.launch {
        studentRepository.insertStudent(student)
    }
}

class StudentViewModelFactory(private val repository: StudentRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}