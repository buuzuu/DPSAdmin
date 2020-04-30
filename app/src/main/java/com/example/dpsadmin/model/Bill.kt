package com.example.dpsadmin.model

data class Bill(

    var billNo:Int,
    var date:String,
    var className:String,
    var name:String,
    var admissionFee:Int,
    var annualCharge:Int,
    var tuitionFee:Int,
    var computerFee:Int,
    var placePrice:Int,
    var examFee:Int,
    var supplementaryFee:Int,
    var bookPrice:Int,
    var lateFee:Int,
    var total:Int,
    var months:String


    ) {
    constructor() : this (0,"","","",0,0,0,0
    ,0,0,0,0,0,0,"")
}