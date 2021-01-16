package com.example.dpsadmin.`interface`

import com.example.dpsadmin.model.NotificationBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationService {

    @POST("notify")
    fun sendNotification(@Body body: NotificationBody): Call<ResponseBody>
}