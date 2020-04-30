package com.example.dpsadmin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsadmin.R
import com.example.dpsadmin.`interface`.OnBookSelectClickListner
import com.example.dpsadmin.model.Book
import java.util.*


class ClassBookGridAdapter(var list: ArrayList<Book>, var listner: OnBookSelectClickListner) :
    RecyclerView.Adapter<ClassBookGridAdapter.ClassBookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassBookViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.book_detail_layout,
            parent, false
        )
        return ClassBookViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ClassBookViewHolder, position: Int) {
        holder.initialize(list[position], listner)

    }

    inner class ClassBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bookName: TextView = itemView.findViewById(R.id.bookName)
        var bookPrice: TextView = itemView.findViewById(R.id.bookPrice)
        var background: LinearLayout = itemView.findViewById(R.id.cardBackground)

        fun initialize(
            book: Book,
            listner: OnBookSelectClickListner
        ) {
            bookName.text = book.bookName
            bookPrice.text = "₹ ${book.bookPrice}"
            background.setBackgroundColor(if (book.isChecked) Color.parseColor("#F6E2FA") else Color.WHITE)

            itemView.setOnClickListener {
                book.isChecked = (!book.isChecked)
                background.setBackgroundColor(if (book.isChecked) Color.parseColor("#F6E2FA") else Color.WHITE)
                var sum = 0
                list.forEach {
                    if (it.isChecked){
                        sum += it.bookPrice
                    }
                }
                if (sum !=0){
                    listner.onBooksSelected(sum)
                }else{
                    listner.onBooksSelected(0)
                }


            }



        }




    }
}