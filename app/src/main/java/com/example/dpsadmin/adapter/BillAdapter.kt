package com.example.dpsadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsadmin.R
import com.example.dpsadmin.`interface`.OnBillStudentClickListner
import com.example.dpsadmin.model.Student
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class BillAdapter(options: FirestoreRecyclerOptions<Student?>, var listner: OnBillStudentClickListner) :
    FirestoreRecyclerAdapter<Student, BillAdapter.BillHolder>(options) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BillHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.bill_student_layout,
            parent, false
        )
        return BillHolder(v)
    }

    override fun onBindViewHolder(holder: BillHolder, position: Int, model: Student) {

        holder.studentName.text = "${model.firstName} ${model.lastName}"
        holder.rollNumber.text = model.rollNumber.toString()
        holder.enrollNo.text = model.enrollmentNumber.toString()
        holder.className.text = model.entryClass
        holder.dob.text = model.dob
        holder.guardian.text = model.fatherName
        holder.address.text = model.address
        holder.itemView.setOnClickListener {
            listner.onStudentForBillClicked(model.enrollmentNumber.toString())
        }
        
    }

    inner class BillHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var studentName: TextView = itemView.findViewById(R.id.studentName)
        var rollNumber: TextView = itemView.findViewById(R.id.rollNumber)
        var enrollNo: TextView = itemView.findViewById(R.id.enrollNo)
        var className: TextView = itemView.findViewById(R.id.className)
        var dob: TextView = itemView.findViewById(R.id.dob)
        var guardian: TextView = itemView.findViewById(R.id.guardian)
        var address: TextView = itemView.findViewById(R.id.address)

    }




}