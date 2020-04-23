package com.example.dpsadmin

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.dpsadmin.ui.dashboard.BillFragment
import com.example.dpsadmin.ui.home.HomeFragment
import com.example.dpsadmin.ui.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fragment: Fragment
    lateinit var myWindow: Window
    private lateinit var toolbar: Toolbar
    // adb connect 192.168.1.8:5555
    // scrcpy -b2M -m1000 --max-fps 15
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        myWindow = this.window
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, HomeFragment())
            .commit()
        bubble.setNavigationChangeListener { _, position ->
            when(position){
                0 ->
                    fragment = HomeFragment()
                1 ->
                    fragment = BillFragment()
                2 ->
                    fragment = NotificationsFragment()
            }
            changeStatusColor(position)
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment,fragment).commit()
        }

    }


    private fun changeStatusColor(pos:Int){
        when (pos) {
            0 -> {
                myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                myWindow.statusBarColor = getColor(R.color.home)
                toolbar.setBackgroundColor(getColor(R.color.home))
            }
            1 -> {
                myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                myWindow.statusBarColor = getColor(R.color.bill)
                toolbar.setBackgroundColor(getColor(R.color.bill))

            }
            2 -> {
                myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                myWindow.statusBarColor = getColor(R.color.salary)
                toolbar.setBackgroundColor(getColor(R.color.salary))

            }
        }


    }
}
