package com.example.encare.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.encare.R
import com.example.encare.activity.MainActivity
import com.example.encare.adapters.ViewPagerAppointAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AppointmentsFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private var mainActivity = MainActivity();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mainActivity = activity as MainActivity;
        val mView = inflater.inflate(R.layout.fragment_apointments, container, false)
        tabLayout = mView.findViewById(R.id.tab_layout_appointment)
        viewPager = mView.findViewById(R.id.viewpager_appointment)

        val viewPagerAppointAdapter = ViewPagerAppointAdapter(mainActivity)
        viewPager.adapter = viewPagerAppointAdapter

        TabLayoutMediator(tabLayout,viewPager){tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Currently"
                }
                1 -> {
                    tab.text = "History"
                }
            }
        }.attach()

        return mView
    }
}