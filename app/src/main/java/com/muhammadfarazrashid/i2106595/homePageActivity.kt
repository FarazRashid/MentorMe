package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class homePageActivity : AppCompatActivity(){

        private lateinit var topMentorsRecycler: RecyclerView
        private lateinit var recentMentorsRecycler: RecyclerView
        private lateinit var educationMentorsRecycler: RecyclerView

        private lateinit var topMentors: ArrayList<Mentor>
        private lateinit var recentMentors: ArrayList<Mentor>
        private lateinit var educationMentors: ArrayList<Mentor>
        private lateinit var badgesRecycler: RecyclerView
        private lateinit var badges: ArrayList<Badge>
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.home_page)

            // Initialize top mentors
            topMentorsRecycler = findViewById(R.id.topMentorsRecycler)
            topMentorsRecycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            topMentors = ArrayList()
            topMentors.add(Mentor("Faraz Rashid", "Android Developer", "available", "$5000/hr"))
            topMentors.add(Mentor("John Doe", "UX Designer", "unavailable", "$7000/hr"))
            topMentors.add(Mentor("Jane Doe", "UI Designer", "available", "$6000/hr"))
            topMentors.add(Mentor("John Smith", "Web Developer", "unavailable", "$8000/hr"))
            topMentors.add(Mentor("Jane Smith", "Web Developer", "available", "$9000/hr"))

            // Choose the layout resource ID based on the card type (horizontal or vertical)
            val horizontalLayoutResourceId = R.layout.mentorcard
            val verticalLayoutResourceId = R.layout.verticalmentorcards

            val topMentorsAdapter = MentorCardAdapter(this, topMentors, horizontalLayoutResourceId)
            topMentorsRecycler.adapter = topMentorsAdapter

            // Initialize recent mentors
            recentMentorsRecycler = findViewById(R.id.recentMentorsRecycler)
            recentMentorsRecycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recentMentors = ArrayList()
            recentMentors.add(Mentor("Mentor 1", "Job 1", "available", "$6000/hr"))
            recentMentors.add(Mentor("Mentor 2", "Job 2", "unavailable", "$7000/hr"))
            val recentMentorsAdapter =
                MentorCardAdapter(this, recentMentors, horizontalLayoutResourceId)
            recentMentorsRecycler.adapter = recentMentorsAdapter

            // Initialize education mentors
            educationMentorsRecycler = findViewById(R.id.educationMentorsRecycler)
            educationMentorsRecycler.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            educationMentors = ArrayList()
            educationMentors.add(Mentor("Mentor 1", "Title 3", "available", "$8000/hr"))
            educationMentors.add(Mentor("2", "Title 4", "unavailable", "$9000/hr"))
            val educationMentorsAdapter =
                MentorCardAdapter(this, educationMentors, horizontalLayoutResourceId)
            educationMentorsRecycler.adapter = educationMentorsAdapter

            badges = ArrayList()
            badges.add(Badge("Category 1", false))
            badges.add(Badge("Category 2", false))
            badges.add(Badge("Category 3", true)) // This badge is selected
            badges.add(Badge("Category 4", false))
            badges.add(Badge("Category 5", false))

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


            val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

            bottomNavigation.setOnNavigationItemReselectedListener { item ->
                when (item.itemId) {
                    R.id.menu_search -> {
                        val intent = Intent(this, searchPageActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.menu_home -> {
                        val intent = Intent(this, homePageActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.menu_chat -> {
                        val intent = Intent(this, mainChatActivity::class.java)
                        startActivity(intent)
                    }

                }

            }
        }
}