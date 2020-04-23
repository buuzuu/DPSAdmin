package com.example.dpsadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsadmin.R
import com.example.dpsadmin.model.Book
import java.util.ArrayList

class ClassBookGridAdapter(var list: ArrayList<Book>) :
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
        holder.initialize(list[position])
    }

    inner class ClassBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bookName: TextView = itemView.findViewById(R.id.bookName)
        var bookPrice: TextView = itemView.findViewById(R.id.bookPrice)

        fun initialize(book: Book) {
            bookName.text = book.bookName
            bookPrice.text = "₹ ${book.bookPrice}"
        }

    }
}