package com.example.dpsadmin.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class NotificationBody(
    @SerializedName("registration_ids")
    var registration_ids : ArrayList<String>,
    @SerializedName("title")
    var title:String,
    @SerializedName("serverKey")
    var serverKey:String,
    @SerializedName("msg")
    var msg:String

)