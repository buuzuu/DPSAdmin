package com.example.dpsadmin.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.dpsadmin.AttendanceActivity
import com.example.dpsadmin.Common
import com.example.dpsadmin.R
import com.example.dpsadmin.StudentRegistration
import com.example.dpsadmin.model.Attendance
import com.example.dpsadmin.model.Student
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.update_attendance_sheet.*
import kotlinx.android.synthetic.main.update_attendance_sheet.view.*
import kotlinx.android.synthetic.main.update_attendance_sheet.view.month
import java.util.ArrayList

class UpdateAttendanceSheet : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var classSpinner: Spinner
    private lateinit var monthSpinner: Spinner
    val db = Firebase.firestore
    var selectedClass: String = ""
    var selectedMonth: String = ""
    private lateinit var dialog: android.app.AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.update_attendance_sheet, container, false)
        classSpinner = v.findViewById(R.id.spinner)
        monthSpinner = v.findViewById(R.id.spinner2)
        classSpinner.onItemSelectedListener = this
        monthSpinner.onItemSelectedListener = this
        dialog =
            SpotsDialog.Builder().setContext(v.context).setTheme(R.style.Custom)
                .setMessage("Generating ...")
                .setCancelable(false).build()
        ArrayAdapter.createFromResource(
            v.context,
            R.array.class_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            classSpinner.adapter = arrayAdapter
        }
        ArrayAdapter.createFromResource(
            v.context,
            R.array.month_array,
            android.R.layout.simple_spinner_item
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            monthSpinner.adapter = arrayAdapter
        }
        v.generate.setOnClickListener {

            db.collection("attendance").document(selectedClass).collection(selectedMonth)
                .get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        var doc: QuerySnapshot? = task.result
                        if (doc!!.isEmpty) {
                            doesnotExits()
                        } else {
                            doesExist()
                        }
                    } else {
                        Toast.makeText(v.context, "Task Result Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        return v
    }

    fun doesnotExits() {
        if (holidays.editText?.text?.isEmpty()!! || month.editText?.text?.isEmpty()!!) {
            Toast.makeText(activity, "Please fill the details", Toast.LENGTH_SHORT).show()
        } else {
            dialog.show()
            db.collection(selectedClass).orderBy("rollNumber", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    var classList: ArrayList<Student> = ArrayList()
                    var stuObj: Student
                    for (document in result) {
                        stuObj = document.toObject(Student::class.java)
                        classList.add(stuObj)
                    }
                    generateAttendanceList(classList)

                }
                .addOnFailureListener { exception ->
                    Toast.makeText(activity, "This Class Data Not Available", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    }

    fun doesExist() {
        dialog.show()
        db.collection("attendance").document(selectedClass).collection(selectedMonth).get()
            .addOnSuccessListener { result ->
                var list: ArrayList<Attendance> = ArrayList()
                var attend: Attendance
                for (document in result) {
                    attend = document.toObject(Attendance::class.java)
                    list.add(attend)
                }
                Common.attendanceList = list
                dialog.dismiss()
                Common.updateAttendanceSheet.dismiss()
                val intent = Intent(activity,AttendanceActivity::class.java)
                intent.putExtra("selectedClass",selectedClass)
                intent.putExtra("selectedMonth",selectedMonth)
                intent.putExtra("status", "false")
                startActivity(intent)
            }
            .addOnFailureListener {
                dialog.dismiss()
            }
    }


    private fun generateAttendanceList(classList: ArrayList<Student>) {
        var tempAttendanceList: ArrayList<Attendance> = ArrayList()
        var obj: Attendance
        for (student in classList) {
            obj = Attendance(
                0,
                holidays.editText?.text.toString().toInt(),
                month.editText?.text.toString().toInt(),
                month.editText?.text.toString().toInt() - holidays.editText?.text.toString()
                    .toInt(),
                0, student.image, "${student.firstName} ${student.lastName}",
                student.entryClass, student.rollNumber, student.enrollmentNumber
            )
            tempAttendanceList.add(obj)
        }
        sendAttendanceToDB(tempAttendanceList)
        Common.attendanceList = tempAttendanceList

    }

    private fun sendAttendanceToDB(tempAttendanceList: ArrayList<Attendance>) {
        for (a in tempAttendanceList) {
            db.collection("attendance").document(selectedClass).collection(selectedMonth)
                .document(a.enrollNo.toString()).set(a)
        }
        dialog.dismiss()
        Common.updateAttendanceSheet.dismiss()
        val intent = Intent(activity,AttendanceActivity::class.java)
        intent.putExtra("selectedClass",selectedClass)
        intent.putExtra("selectedMonth",selectedMonth)
        intent.putExtra("status", "false")
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        if (parent?.count.toString().toInt() == 11) {
            selectedClass = decideCollection(parent?.getItemAtPosition(position).toString())

        } else if (parent?.count.toString().toInt() == 12) {
            selectedMonth = parent?.getItemAtPosition(position).toString()
        }

    }

    fun decideCollection(student: String): String {

        when (student) {
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

}