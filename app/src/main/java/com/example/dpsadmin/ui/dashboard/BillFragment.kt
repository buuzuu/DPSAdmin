package com.example.dpsadmin.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dpsadmin.AttendanceActivity
import com.example.dpsadmin.BillStudentActivity
import com.example.dpsadmin.R
import kotlinx.android.synthetic.main.fragment_bill.view.*

class BillFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_bill, container, false)
        root.pg.setOnClickListener {
            startActivity("class_playGroup")
        }
        root.un.setOnClickListener {
            startActivity("class_upperNursery")

        }
        root.ln.setOnClickListener {
            startActivity("class_lowerNursery")

        }
        root.one.setOnClickListener {
            startActivity("class_one")

        }
        root.two.setOnClickListener {
            startActivity("class_two")

        }
        root.three.setOnClickListener {
            startActivity("class_three")

        }
        root.four.setOnClickListener {
            startActivity("class_four")

        }
        root.five.setOnClickListener{
            startActivity("class_five")

        }
        root.six.setOnClickListener {
            startActivity("class_six")

        }
        root.seven.setOnClickListener {
            startActivity("class_seven")

        }
        root.eight.setOnClickListener {
            startActivity("class_eight")

        }

        return root
    }

    fun startActivity(s:String){
        val intent = Intent(activity, BillStudentActivity::class.java)
        intent.putExtra("collection", s)
        startActivity(intent)
    }
}
