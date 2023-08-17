package com.AddonWebSolutions.PracticalTask.Activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.AddonWebSolutions.PracticalTask.Database.SqLiteDB
import com.AddonWebSolutions.PracticalTask.R


class StudentRegisterActivity : AppCompatActivity() {

    private var ImagePath: String = ""
    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 0
    private lateinit var edStudentName: EditText
    private lateinit var txtSelectImage: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var imgSelectedImage: ImageView
    private lateinit var btnAddStudent: Button
    lateinit var db: SqLiteDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_register)

        edStudentName = findViewById(R.id.ed_student_name)
        txtSelectImage = findViewById(R.id.txt_select_image)
        rgGender = findViewById(R.id.rg_gender)
        imgSelectedImage = findViewById(R.id.img_selected_image)
        btnAddStudent = findViewById(R.id.btn_add_student)
        db = SqLiteDB(this, null)

        txtSelectImage.setOnClickListener {
            if (checkAndRequestPermissions(this)) {
                chooseImage(this)
            }
        }

        btnAddStudent.setOnClickListener {
            val selectedId: Int = rgGender.checkedRadioButtonId
            var selectedGender = 0
            if (selectedId == R.id.rb_male) {
                selectedGender = 1
            } else if (selectedId == R.id.rb_female) {
                selectedGender = 2
            }
            if (edStudentName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Student Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedGender == 0) {
                Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (ImagePath == "") {
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            db.addStudent(edStudentName.text.toString(), selectedGender, ImagePath)
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    applicationContext,
                    "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT
                )
                    .show()
            } else if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    applicationContext,
                    "FlagUp Requires Access to Your Storage.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                chooseImage(this)
            }
        }
    }

    fun checkAndRequestPermissions(context: Activity?): Boolean {
        val WExtstorePermission = ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val cameraPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                .add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                context, listPermissionsNeeded
                    .toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    private fun chooseImage(context: Context) {
        val optionsMenu = arrayOf<CharSequence>(
            "Choose from Gallery",
            "Exit"
        )
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setItems(optionsMenu,
            DialogInterface.OnClickListener { dialogInterface, i ->
                if (optionsMenu[i] == "Choose from Gallery") {
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, 1)
                } else if (optionsMenu[i] == "Exit") {
                    dialogInterface.dismiss()
                }
            })
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode !== RESULT_CANCELED) {
            when (requestCode) {
                1 -> if (resultCode === RESULT_OK) {
                    val selectedImage: Uri = data!!.data!!
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor: Cursor? =
                        contentResolver.query(selectedImage, filePathColumn, null, null, null)
                    if (cursor != null) {
                        cursor.moveToFirst()
                        val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                        ImagePath = cursor.getString(columnIndex)
                        imgSelectedImage.setImageBitmap(BitmapFactory.decodeFile(ImagePath))
                        cursor.close()
                    }
                }
            }
        }
    }
}