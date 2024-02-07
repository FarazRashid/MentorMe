package com.muhammadfarazrashid.i2106595

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class calenderPage: AppCompatActivity() {

    private lateinit var badgesRecycler: RecyclerView
    private lateinit var badges: ArrayList<Badge>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calenderpage)

        //add availability timings

        badges = ArrayList()
        badges.add(Badge("11:00 AM", false))
        badges.add(Badge("12:00 PM", false))
        badges.add(Badge("10:00 AM", true)) // This badge is selected

        badgesRecycler = findViewById(R.id.badgesRecycler)
        badgesRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        val badgeAdapter = BadgeAdapter(badges)
        badgesRecycler.adapter = badgeAdapter

        // Set the badge click listener
        badgeAdapter.setOnBadgeClickListener(object : BadgeAdapter.OnBadgeClickListener {
            override fun onBadgeClick(position: Int) {
                // Handle badge click
                Log.d("MainActivity", "Badge clicked at position: $position")
            }
        })


    }
}