package com.example.dpsadmin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsadmin.R
import com.example.dpsadmin.model.Message
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MsgAdapter(option : FirestoreRecyclerOptions<Message>) : FirestoreRecyclerAdapter<Message,MsgAdapter.MsgViewHolder>(option) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgAdapter.MsgViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.message_layout,
            parent, false
        )
        return MsgViewHolder(v)
    }

    override fun onBindViewHolder(holder: MsgAdapter.MsgViewHolder, position: Int, model: Message) {
        holder.date.text = model.date
        holder.msg.text = model.msg


    }
    inner class MsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date)
        var msg: TextView = itemView.findViewById(R.id.msg)

    }
}