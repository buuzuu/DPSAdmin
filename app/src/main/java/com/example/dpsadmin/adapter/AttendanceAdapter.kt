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

class AttendanceAdapter(var list:ArrayList<Attendance>, var listner: OnAttendanceClickListner) : RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): AttendanceAdapter.AttendanceViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.attendance_layout,
            parent, false
        )
        return AttendanceViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AttendanceAdapter.AttendanceViewHolder, position: Int) {
        holder.initalize(list[position], listner)
    }

    inner class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val studentName: TextView = itemView.findViewById(R.id.studentName)
        val rollNumber:TextView = itemView.findViewById(R.id.rollNumber)
        val className:TextView = itemView.findViewById(R.id.className)
        val total:TextView = itemView.findViewById(R.id.total)
        val open:TextView = itemView.findViewById(R.id.open)
        val present:TextView = itemView.findViewById(R.id.present)
        init {
            itemView.setOnClickListener(this)
        }


        fun initalize(
            attendance: Attendance,
            listner: OnAttendanceClickListner
        ) {
            studentName.text = attendance.studentName
            rollNumber.text = "${attendance.rollNo}"
            className.text = attendance.studentClass
            total.text = "${attendance.monthDays} days"
            open.text = "${attendance.openDays} days"
            present.text = "${attendance.presentDays} days"
            Picasso.get().load(attendance.image).into(itemView.image)
            present.setOnClickListener {
                populateView(attendance)
            }

        }

        override fun onClick(v: View?) {
        }
        fun populateView(attendance: Attendance) {
            val mDialogView = LayoutInflater.from(itemView.context).inflate(R.layout.attendance_popup, null)
            val mBuilder = AlertDialog.Builder(itemView.context).setView(mDialogView)
            val  mAlertDialog = mBuilder.show()
            mDialogView.cancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
            mAlertDialog.apply.setOnClickListener {

                listner.opUpdate(adapterPosition, attendance, mDialogView.day.text.toString().toInt())
                present.text = "${mDialogView.day.text.toString().toInt()} days"
                mAlertDialog.dismiss()


            }
        }

    }
}