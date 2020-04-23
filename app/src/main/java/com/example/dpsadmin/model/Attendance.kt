package com.example.dpsadmin.model

data class Attendance(
    var absentDays:Int,
    var holidayDays:Int,
    var monthDays:Int,
    var openDays:Int,
    var presentDays:Int,
    var image:String,
    var studentName:String,
    var studentClass:String,
    var rollNo:Int,
    var enrollNo:Int
) {

    constructor() : this(0,0,0,0,0,"","","",0,0)

}