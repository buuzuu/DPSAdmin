package com.example.dpsadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dpsadmin.`interface`.OnAttendanceClickListner
import com.example.dpsadmin.adapter.AttendanceAdapter
import com.example.dpsadmin.adapter.ViewAttendanceAdapter
import com.example.dpsadmin.model.Attendance
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_attendance.*

class AttendanceActivity : AppCompatActivity(), OnAttendanceClickListner {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbar_title: TextView
    private lateinit var myAdapter: AttendanceAdapter
    private lateinit var myAdapter2: ViewAttendanceAdapter
    val db = Firebase.firestore
    private lateinit var dialog: android.app.AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)
        toolbar = findViewById(R.id.toolbar)
        toolbar_title = toolbar.findViewById(R.id.toolbar_title)
        toolbar_title.text = "Attendance"
        setSupportActionBar(toolbar)
        dialog =
            SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom)
                .setMessage("Updating ...")
                .setCancelable(false).build()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        attendance_rv.layoutManager = LinearLayoutManager(this)
        myAdapter = AttendanceAdapter(Common.attendanceList, this)
        myAdapter2 = ViewAttendanceAdapter(Common.viewAttendanceList)
        if (intent.getStringExtra("status") == "true"){
            attendance_rv.adapter = myAdapter2
        }else{
            attendance_rv.adapter = myAdapter
            myAdapter.notifyDataSetChanged()
        }


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

    override fun opUpdate(position: Int, attendance: Attendance, presentDays: Int) {
        dialog.show()
        val obj = attendance
        obj.presentDays = presentDays
        obj.absentDays = attendance.openDays - presentDays
        db.collection("attendance").document(intent.getStringExtra("selectedClass"))
            .collection(intent.getStringExtra("selectedMonth"))
            .document(attendance.enrollNo.toString()).set(obj).addOnSuccessListener {
                myAdapter.list[position] = obj
                myAdapter.notifyItemChanged(position,obj)
                updateLocally(obj)

                dialog.dismiss()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed To Update", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
    }

    fun updateLocally(attendance: Attendance){
        for ( (index, item) in Common.attendanceList.withIndex()){
                if (item.enrollNo == attendance.enrollNo){
                    Common.attendanceList[index] = attendance
                }
        }
    }
}
