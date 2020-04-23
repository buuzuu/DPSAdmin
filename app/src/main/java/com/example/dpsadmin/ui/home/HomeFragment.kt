package com.example.dpsadmin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dpsadmin.Common
import com.example.dpsadmin.R
import com.example.dpsadmin.StudentRegistration
import com.example.dpsadmin.bottomsheet.UpdateAttendanceSheet
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        root.studentCard.setOnClickListener {
            startActivity(Intent(activity, StudentRegistration::class.java))
        }
        root.updateAttendance.setOnClickListener {
            Common.updateAttendanceSheet.show(activity!!.supportFragmentManager, "UpdateBottomSheet")
        }
        root.viewAttendance.setOnClickListener {
            Common.viewAttendanceSheet.show(activity!!.supportFragmentManager, "ViewBottomSheet")

        }
        return root
    }
}
