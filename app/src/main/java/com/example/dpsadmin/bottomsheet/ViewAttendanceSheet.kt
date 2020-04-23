package com.example.dpsadmin.bottomsheet

import android.content.Intent
import android.os.Bundle
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
import com.example.dpsadmin.model.Attendance
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.view_attendance_sheet.view.*
import java.util.ArrayList

class ViewAttendanceSheet:BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {

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
        val v = inflater.inflate(R.layout.view_attendance_sheet, container, false)
        classSpinner = v.findViewById(R.id.spinner)
        monthSpinner = v.findViewById(R.id.spinner2)
        classSpinner.onItemSelectedListener = this
        monthSpinner.onItemSelectedListener = this
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
        v.viewAttendance.setOnClickListener {
            db.collection("attendance").document(selectedClass).collection(selectedMonth)
                .get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        var doc: QuerySnapshot? = task.result
                        if (doc!!.isEmpty) {
                            Toast.makeText(v.context, "No Attendance Available For The Selected Month", Toast.LENGTH_SHORT).show()
                        } else {
                            doesExist()
                        }
                    } else {
                        Toast.makeText(v.context, "Not Available", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener {
                    Toast.makeText(v.context, "Task Result Failed 2", Toast.LENGTH_SHORT).show()

                }
        }

        return v
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

    fun doesExist(){
        db.collection("attendance").document(selectedClass).collection(selectedMonth).get()
            .addOnSuccessListener { result ->
                var list: ArrayList<Attendance> = ArrayList()
                var attend: Attendance
                for (document in result) {
                    attend = document.toObject(Attendance::class.java)
                    list.add(attend)
                }
                Common.viewAttendanceList = list
                val intent = Intent(activity, AttendanceActivity::class.java)
                intent.putExtra("status", "true")
                startActivity(intent)
            }
            .addOnFailureListener {
            }
    }

}