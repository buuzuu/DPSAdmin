package com.example.dpsadmin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsadmin.R
import com.example.dpsadmin.`interface`.OnMonthClickListner
import com.example.dpsadmin.model.BillFee
import com.example.dpsadmin.model.Fee
import java.util.ArrayList

class MonthAdapter(var list: ArrayList<BillFee>, var listner: OnMonthClickListner) :
    RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.month_layout,
            parent, false
        )
        return MonthViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.initialize(list[position].fee, list[position], listner)
    }

    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var month: TextView = itemView.findViewById(R.id.monthName)
        var bg: LinearLayout = itemView.findViewById(R.id.linear_layout)
        fun initialize(
            fee: Fee,
            billFee: BillFee,
            listner: OnMonthClickListner
        ) {
            month.text = fee.month
            if (fee.isPaid) {
                bg.setBackgroundColor(Color.parseColor("#C6ECA9"))
            } else if (billFee.isChecked && !fee.isPaid) {
                bg.setBackgroundColor(Color.parseColor("#F6E2FA"))
            } else {
                bg.setBackgroundColor(Color.WHITE)
            }

            itemView.setOnClickListener {

            }

            bg.setOnClickListener {
                var sum = 0
                var monthList = ArrayList<String>()
                if (billFee.fee.isPaid) {
                    return@setOnClickListener
                } else if (!billFee.fee.isPaid) {
                    billFee.isChecked = (!billFee.isChecked)
                    bg.setBackgroundColor(if (billFee.isChecked) Color.parseColor("#F6E2FA") else Color.WHITE)
                }
                list.forEach {
                    if (it.isChecked) {
                        sum += it.fee.tuitionFee
                        monthList.add(it.fee.month)
                    }
                }


                if (sum != 0) {
                    listner.onMonthsSelected(sum, monthList)
                } else {
                    listner.onMonthsSelected(0, monthList)
                }


            }


        }

    }
}