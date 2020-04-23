package com.example.dpsadmin.model

data class Book(
    var bookName:String,
    var bookPrice:Int
){
    constructor() : this("",0)
}