package com.example.dpsadmin.model

import java.io.Serializable

data class Place(
    var placeName:String,
    var placePrice:Int,
    var isSelected:Boolean
):Serializable {
    constructor() : this("",0,false)
}