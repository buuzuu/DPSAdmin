package com.example.dpsadmin.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsadmin.HtmlString
import com.example.dpsadmin.PrintWebView
import com.example.dpsadmin.R
import com.example.dpsadmin.model.Bill
import java.util.ArrayList

class PreviousBill(var list: ArrayList<Bill>) :
    RecyclerView.Adapter<PreviousBill.PreviousViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviousViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.previous_bill_layout,
            parent, false
        )
        return PreviousViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PreviousViewHolder, position: Int) {
        holder.initalize(list[position])
    }

    inner class PreviousViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var billNo: TextView = itemView.findViewById(R.id.billNo)
        var billDate: TextView = itemView.findViewById(R.id.date)
        var billAmount: TextView = itemView.findViewById(R.id.amount)

        fun initalize(bill: Bill) {
            billNo.text =bill.billNo.toString()
            billAmount.text =  "₹ ${bill.total}"
            billDate.text = bill.date

            itemView.setOnClickListener {
                HtmlString.pdfStr = HtmlString.getHtmlForPdf(
                    bill.billNo, bill.date, bill.className, bill.name,
                    bill.admissionFee, bill.annualCharge, bill.tuitionFee, bill.computerFee, bill.placePrice, bill.examFee,
                    bill.supplementaryFee,
                    bill.bookPrice, 0, bill.total, bill.months
                )
                val i = Intent(itemView.context, PrintWebView::class.java)
                itemView.context.startActivity(i)
            }
        }




    }
}