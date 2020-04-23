package com.example.dpsadmin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.dpsadmin.model.Student
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_student_registration.*
import java.io.ByteArrayOutputStream


class StudentRegistration : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner
    var selectedClass: String = ""
    var gender: String = ""
    var imageURL: String = ""
    var bol_i: Boolean = false
    private lateinit var dialog: android.app.AlertDialog
    var bol_j: Boolean = false
    val CAM_REQUEST = 100
    val db = Firebase.firestore
    val storage = Firebase.storage("gs://dpsadmin-339a7.appspot.com")
    var imagesRef: StorageReference? = storage.reference.child("studentImages")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_registration)
        toolbar = findViewById(R.id.toolbar)
        spinner = findViewById(R.id.spinner)
        spinner2 = findViewById(R.id.spinnerGender)
        spinner.onItemSelectedListener = this
        spinner2.onItemSelectedListener = this
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        dialog =
            SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).setMessage("Saving ...")
                .setCancelable(false).build()

        ArrayAdapter.createFromResource(
            this,
            R.array.class_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = arrayAdapter
        }
        save.setOnClickListener {
            var s = decideCollection(getFormData())
            if (s == "") {
                Toast.makeText(this, "Important Field Missing", Toast.LENGTH_SHORT).show()
            } else {
                dialog.show()
                var stu = getFormData()
                saveImageToDB(s, stu, stu!!.enrollmentNumber)

            }
        }

        uploadImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAM_REQUEST)

        }
    }

    fun saveImageToDB(
        s: String,
        stu: Student?,
        enrollmentNumber: Int
    ) {
        profileImage.isDrawingCacheEnabled = true
        profileImage.buildDrawingCache()
        val bitmap = (profileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var uploadTask = imagesRef?.child("$enrollmentNumber.jpg")?.putBytes(data)
        uploadTask?.addOnFailureListener {
            Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
            stu?.image = ""
            saveToDB(s, stu)
            dialog.dismiss()
        }?.addOnSuccessListener {
            imagesRef?.child("$enrollmentNumber.jpg")?.downloadUrl?.addOnSuccessListener {
                stu?.image = it.toString()
                saveToDB(s, stu)
            }
        }
    }

    private fun saveToDB(s: String, formData: Student?) {
        db.collection(s).document(formData?.enrollmentNumber.toString()).set(formData!!)
            .addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error writing document", e)
                dialog.dismiss()
            }
    }

    override fun onStart() {
        super.onStart()
        bol_i = false
        bol_j = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("TAG", parent!!.getItemAtPosition(position).toString())
        if (parent.count.toString().toInt() == 11){
            selectedClass = parent.getItemAtPosition(position).toString()

        }else if (parent.count.toString().toInt() == 2) {
            gender = parent.getItemAtPosition(position).toString()
        }

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_yes1 ->
                    if (checked) {
                        bol_i = true
                    }
                R.id.radio_no1 ->
                    if (checked) {
                        bol_i = false
                    }
                R.id.radio_yes2 ->
                    if (checked) {
                        bol_j = true
                    }
                R.id.radio_no2 ->
                    if (checked) {
                        bol_j = false
                    }

            }
        }
    }

    private fun getFormData(): Student? {

        if (enroll.editText?.text.toString().isEmpty() ||
            firstName.editText?.text.toString().isEmpty() ||
            dob.editText?.text.toString().isEmpty() ||
            doa.editText?.text.toString().isEmpty() ||
            religion.editText?.text.toString().isEmpty() ||
            mobile.editText?.text.toString().isEmpty() || address.editText?.text.toString()
                .isEmpty()
        ) {
            Toast.makeText(this, "Important field missing", Toast.LENGTH_LONG).show()
            return null
        } else {

            return Student(
                enrollmentNumber = enroll.editText?.text.toString().toInt(),
                rollNumber = (if (rollNo.editText?.text.toString()
                        .isEmpty()
                ) "" else rollNo.editText?.text.toString().toInt()) as Int,
                firstName = firstName.editText?.text.toString(),
                lastName = lastName.editText?.text.toString(),
                motherName = motherName.editText?.text.toString(),
                fatherName = fatherName.editText?.text.toString(),
                dob = dob.editText?.text.toString(),
                doa = doa.editText?.text.toString(),
                entryClass = selectedClass,
                schoolAttended = schoolAttended.editText?.text.toString(),
                religion = religion.editText?.text.toString(),
                occupation = occupation.editText?.text.toString(),
                mobileNumber = mobile.editText?.text.toString().toLong(),
                address = address.editText?.text.toString(),
                aadharNumber = (if (aadhar.editText?.text.toString()
                        .isEmpty()
                ) "0" else aadhar.editText?.text.toString().toLong()) as Long,
                newStudent = bol_i,
                transportStudent = bol_j,
                image = imageURL,
                gender = gender
            )
        }

        return null

    }

    fun decideCollection(student: Student?): String {

        when (student?.entryClass) {
            "Play Group" -> {
                return "class_playGroup"
            }
            "Lower Nursery" -> {
                return "class_lowerNursery"
            }
            "Upper Nursery" -> {
                return "class_upperNursery"
            }
            "Class One" -> {
                return "class_one"
            }
            "Class Two" -> {
                return "class_two"
            }
            "Class Three" -> {
                return "class_three"
            }
            "Class Four" -> {
                return "class_four"
            }
            "Class Five" -> {
                return "class_five"
            }
            "Class Six" -> {
                return "class_six"
            }
            "Class Seven" -> {
                return "class_seven"
            }
            "Class Eight" -> {
                return "class_eight"
            }
        }
        return ""

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAM_REQUEST) {
                var bitmap = data?.extras?.get("data") as Bitmap
                val bytes = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                profileImage.setImageBitmap(bitmap)
            }


        }


    }

}
