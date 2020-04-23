package com.example.dpsadmin.`interface`

import com.example.dpsadmin.model.Attendance

interface OnAttendanceClickListner {
    fun opUpdate(position:Int, attendance: Attendance, presentDays:Int)
}