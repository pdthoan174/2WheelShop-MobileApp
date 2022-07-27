package com.example.encare.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.encare.*
import com.example.encare.models.Appointment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()
    private var appointmentFragment = ApointmentsFragment()
    private var messageFragment = MessageFragment()
    private var profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        replaceFragment(homeFragment)
        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> {
                    replaceFragment(homeFragment)
                }
                R.id.nav_appointment -> {
                    replaceFragment(appointmentFragment)
                }
                R.id.nav_message -> {
                    replaceFragment(messageFragment)
                }
                R.id.nav_profile -> {
                    replaceFragment(profileFragment)
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        if (fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}