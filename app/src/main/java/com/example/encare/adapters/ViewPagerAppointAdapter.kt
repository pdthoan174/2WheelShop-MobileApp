package com.example.encare.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.encare.fragments.AppointmentHistoryFragment
import com.example.encare.fragments.AppointmentCurrentFragment

class ViewPagerAppointAdapter(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AppointmentCurrentFragment()
            1 -> return AppointmentHistoryFragment()
            else -> return AppointmentCurrentFragment()
        }
    }
}