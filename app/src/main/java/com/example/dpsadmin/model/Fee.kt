package com.example.dpsadmin.model

import java.io.Serializable

data class Fee(
    var isPaid :Boolean,
    var month :String,
    var nom:Int,
    var tuitionFee:Int,
    var timestamp:String

): Serializable
{
    constructor() : this(false,"",0,0,"")
}