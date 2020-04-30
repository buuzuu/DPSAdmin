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
import com.example.dpsadmin.`interface`.OnPlaceClickListner
import com.example.dpsadmin.model.Place
import java.util.*

class PlaceAdapter(var list: ArrayList<Place>, var listner: OnPlaceClickListner) :
    RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.place_layout,
            parent, false
        )
        return PlaceViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.initalize(list[position], listner)
    }

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.placeName)
        var price: TextView = itemView.findViewById(R.id.placePrice)
        var bg: LinearLayout = itemView.findViewById(R.id.linear_layout)

        fun initalize(
            place: Place,
            listner: OnPlaceClickListner
        ) {
            name.text = place.placeName
            price.text = "₹ ${place.placePrice}"
            bg.setBackgroundColor(if (place.isSelected) Color.parseColor("#F6E2FA") else Color.WHITE)

            itemView.setOnClickListener {

                place.isSelected = (!place.isSelected)
                bg.setBackgroundColor(if (place.isSelected) Color.parseColor("#F6E2FA") else Color.WHITE)
                var sum = 0
                list.forEach {
                    if (it.isSelected) {
                        sum += it.placePrice
                    }
                }
                if (sum != 0) {
                    listner.onPlaceSelected(sum)
                } else {
                    listner.onPlaceSelected(0)
                }


            }


        }

    }
}