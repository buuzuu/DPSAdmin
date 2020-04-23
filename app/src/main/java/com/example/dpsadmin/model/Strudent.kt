package com.example.dpsadmin.model


data class Student(

    var enrollmentNumber: Int,
    var rollNumber:Int,
    var firstName:String,
    var lastName:String,
    var motherName:String,
    var fatherName:String,
    var dob:String,
    var doa:String,
    var entryClass:String,
    var schoolAttended:String,
    var religion:String,
    var occupation:String,
    var mobileNumber:Long,
    var address:String,
    var aadharNumber:Long,
    var newStudent:Boolean,
    var transportStudent:Boolean,
    var image:String,
    var gender:String


){
    constructor() : this(0,0,"","","","","","",""
    ,"","","",0,"",0,true,true,"","")
}