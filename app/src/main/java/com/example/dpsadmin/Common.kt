package com.example.dpsadmin

import com.example.dpsadmin.bottomsheet.UpdateAttendanceSheet
import com.example.dpsadmin.bottomsheet.ViewAttendanceSheet
import com.example.dpsadmin.model.Attendance
import java.util.*
import kotlin.collections.ArrayList

class Common {


    companion object {
        var a: Int = 0
        var attendanceList: ArrayList<Attendance> = java.util.ArrayList()
        var viewAttendanceList: ArrayList<Attendance> = java.util.ArrayList()
        var updateAttendanceSheet = UpdateAttendanceSheet()
        var viewAttendanceSheet = ViewAttendanceSheet()

        fun getYear(now: Calendar): String {
            return now.get(Calendar.YEAR).toString()
        }

        fun getMonth(now: Calendar): String {
            var a = now.get(Calendar.MONTH) + 1
            return a.toString()
        }
        fun getDay(now: Calendar): String {
            return now.get(Calendar.DATE).toString()
        }

    }
}