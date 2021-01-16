package com.example.dpsadmin

import com.example.dpsadmin.bottomsheet.UpdateAttendanceSheet
import com.example.dpsadmin.bottomsheet.ViewAttendanceSheet
import com.example.dpsadmin.model.Attendance
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class Common {


    companion object {
        var monthArray = arrayOf<String>("January","February","March","April","May", "June","July","August","September","October","November","December")
        var a: Int = 0
        var attendanceList: ArrayList<Attendance> = java.util.ArrayList()
        var viewAttendanceList: ArrayList<Attendance> = java.util.ArrayList()
        var updateAttendanceSheet = UpdateAttendanceSheet()
        var viewAttendanceSheet = ViewAttendanceSheet()

        fun getYear(now: Calendar): String {
            return now.get(Calendar.YEAR).toString()
        }

        fun getMonth(now: Calendar): String {
            var a = now.get(Calendar.MONTH) + 1
            return a.toString()
        }
        fun getDay(now: Calendar): String {
            return now.get(Calendar.DATE).toString()
        }
        val generalRetrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pankaj-oil-api.herokuapp.com/")
            .build()!!


    }
}