package com.muhammadfarazrashid.i2106595


import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class calendarPage : AppCompatActivity() {

    private lateinit var badgesRecycler: RecyclerView
    private lateinit var badgeAdapter: BadgeAdapter
    private lateinit var mentorName: TextView
    private lateinit var mentorImage: ImageView
    private lateinit var currentMentor: Mentor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calenderpage)
        currentMentor = intent.getParcelableExtra<Mentor>("mentor")!!
        initializeViews()
        setMentorDetails(currentMentor)
        setUpAvailability()
        setUpOnClickListeners()
    }

    private fun setMentorDetails(mentor: Mentor) {
        mentorName.text = mentor.name
        currentMentor = mentor

        // Call getImageUrl function to get the mentor's image URL
        Mentor.getImageUrl(mentor.id, object : Mentor.OnImageUrlListener {
            override fun onSuccess(imageUrl: String) {
                // Load image using Picasso
                Picasso.get().load(imageUrl).into(mentorImage)
            }

            override fun onFailure(errorMessage: String) {
                // Handle failure to retrieve image URL
                Log.e("MentorCardAdapter", "Failed to retrieve image URL: $errorMessage")
            }
        })
    }

    private fun setUpAvailability() {
        // Set up badges
        val badges = arrayListOf(
            Badge("11:00 AM", false),
            Badge("12:00 PM", false),
            Badge("10:00 AM", true) // This badge is selected
        )

        badgeAdapter = BadgeAdapter(badges)
        badgesRecycler.adapter = badgeAdapter

        // Set the badge click listener
        badgeAdapter.setOnBadgeClickListener(object : BadgeAdapter.OnBadgeClickListener {
            override fun onBadgeClick(position: Int) {
                // Handle badge click
                Log.d("CalendarPage", "Badge clicked at position: $position")
            }
        })
    }

    private fun initializeViews() {
        badgesRecycler = findViewById(R.id.badgesRecycler)
        badgesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mentorName= findViewById(R.id.mentorName)
        mentorImage = findViewById(R.id.imageView9)

    }
    private fun navigateToChatPage(mentor: Mentor) {
        val intent = Intent(this, MentorChatActivity::class.java)
        intent.putExtra("mentor", mentor)
        startActivity(intent)
    }



    private fun setUpOnClickListeners()
    {
        findViewById<ImageView>(R.id.chatButton).setOnClickListener {
            navigateToChatPage(currentMentor)
        }

        findViewById<ImageView>(R.id.phoneButton).setOnClickListener {
            startActivity(Intent(this, PhoneCallActivity::class.java))
        }

        findViewById<ImageView>(R.id.cameraButton).setOnClickListener {
            startActivity(Intent(this, VideoCallActivity::class.java))
        }

        findViewById<ImageView>(R.id.imageView4).setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.bookAnAppointmentButton).setOnClickListener {
            startActivity(Intent(this, homePageActivity::class.java))
        }
    }
}
