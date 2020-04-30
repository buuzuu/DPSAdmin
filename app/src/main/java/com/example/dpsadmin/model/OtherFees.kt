package com.example.dpsadmin.model

data class OtherFees(
    var admissionCharge:Int,
    var annualCharge:Int,
    var beltPrice:Int,
    var computerFeeJunior:Int,
    var computerFeeSenior:Int,
    var diaryFee:Int,
    var examFee:Int,
    var iandfeeCardPrice:Int,
    var tieFeeJunior:Int,
    var tieFeeSenior:Int,
    var verificationPassword:String

) {
    constructor() : this(0,0,0,0,0,0,0,0,0,0,"")
}