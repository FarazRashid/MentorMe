package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageView
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

        //click on chat image button button and go to chat page

        val chat = findViewById<ImageView>(R.id.chatButton)

        chat.setOnClickListener {
            val intent = Intent(this, mainChatActivity::class.java)
            startActivity(intent)
        }

        //click on phone button and go to phone page

        val phone = findViewById<ImageView>(R.id.phoneButton)

        phone.setOnClickListener {
            val intent = Intent(this, PhoneCallActivity::class.java)
            startActivity(intent)
        }

        //click on videobutton and go to videocall page

        val video = findViewById<ImageView>(R.id.cameraButton)

        video.setOnClickListener {
            val intent = Intent(this, VideoCallActivity::class.java)
            startActivity(intent)
        }

        //click on imageview4 and go back

        val bottomNavigationView = findViewById<ImageView>(R.id.imageView4)

        bottomNavigationView.setOnClickListener {
            onBackPressed()
        }

        //click on book an appointmentbutton and go back to home

        val bookAppointment = findViewById<Button>(R.id.bookAnAppointmentButton)

        bookAppointment.setOnClickListener {
            val intent = Intent(this, homePageActivity::class.java)
            startActivity(intent)
        }


    }
}