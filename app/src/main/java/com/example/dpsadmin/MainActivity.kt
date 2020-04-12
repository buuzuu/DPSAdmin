package com.example.dpsadmin

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dpsadmin.ui.dashboard.DashboardFragment
import com.example.dpsadmin.ui.home.HomeFragment
import com.example.dpsadmin.ui.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {

    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, HomeFragment())
            .commit()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = HomeFragment()
                    //return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    fragment = DashboardFragment()
                    //return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    fragment = NotificationsFragment()
                    //return@setOnNavigationItemSelectedListener true
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
            false
        }

    }
}
