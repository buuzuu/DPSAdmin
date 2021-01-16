package com.example.dpsadmin.model

data class Message(
    var timestamp:String,
    var date:String,
    var msg:String
){
    constructor(): this ( "", "","")
}