package com.example.dpsadmin.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsadmin.R
import com.example.dpsadmin.`interface`.OnAttendanceClickListner
import com.example.dpsadmin.model.Attendance
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.attendance_layout.view.*
import kotlinx.android.synthetic.main.attendance_popup.*
import kotlinx.android.synthetic.main.attendance_popup.view.*
import java.util.ArrayList

class ViewAttendanceAdapter(var list:ArrayList<Attendance>) : RecyclerView.Adapter<ViewAttendanceAdapter.AttendanceViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewAttendanceAdapter.AttendanceViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.attendance_layout,
            parent, false
        )
        return AttendanceViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewAttendanceAdapter.AttendanceViewHolder, position: Int) {
        holder.initalize(list[position])
    }

    inner class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.studentName)
        val rollNumber:TextView = itemView.findViewById(R.id.rollNumber)
        val className:TextView = itemView.findViewById(R.id.className)
        val total:TextView = itemView.findViewById(R.id.total)
        val open:TextView = itemView.findViewById(R.id.open)
        val present:TextView = itemView.findViewById(R.id.present)



        fun initalize(
            attendance: Attendance
        ) {
            studentName.text = attendance.studentName
            rollNumber.text = "${attendance.rollNo}"
            className.text = attendance.studentClass
            total.text = "${attendance.monthDays} days"
            open.text = "${attendance.openDays} days"
            present.text = "${attendance.presentDays} days"
            Picasso.get().load(attendance.image).into(itemView.image)


        }

    }
}