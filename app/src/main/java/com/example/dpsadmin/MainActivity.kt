package com.example.dpsadmin

import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dpsadmin.ui.dashboard.DashboardFragment
import com.example.dpsadmin.ui.home.HomeFragment
import com.example.dpsadmin.ui.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fragment: Fragment
    lateinit var myWindow: Window
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myWindow = this.window
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, HomeFragment())
            .commit()

        bubble.setNavigationChangeListener { _, position ->
            when(position){
                0 ->
                    fragment = HomeFragment()
                1 ->
                    fragment = DashboardFragment()
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
            }
            1 -> {
                myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                myWindow.statusBarColor = getColor(R.color.bill)
            }
            2 -> {
                myWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                myWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                myWindow.statusBarColor = getColor(R.color.salary)
            }
        }


    }
}
