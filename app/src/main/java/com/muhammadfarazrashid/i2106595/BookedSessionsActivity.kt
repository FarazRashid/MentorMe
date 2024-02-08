package com.muhammadfarazrashid.i2106595

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BookedSessionsActivity : AppCompatActivity() {

    private lateinit var bookedSessionsRecyclerView: RecyclerView
    private lateinit var bookedSessionsAdapter: MentorCardAdapter
    private lateinit var topMentors: ArrayList<Mentor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_sessions)

        // Initialize top mentors
        topMentors = ArrayList()
        topMentors.add(Mentor("Faraz Rashid", "Android Developer", "available", "$5000/hr"))
        topMentors.add(Mentor("John Doe", "UX Designer", "unavailable", "$7000/hr"))
        topMentors.add(Mentor("Jane Doe", "UI Designer", "available", "$6000/hr"))
        topMentors.add(Mentor("John Smith", "Web Developer", "unavailable", "$8000/hr"))
        topMentors.add(Mentor("Jane Smith", "Web Developer", "available", "$9000/hr"))

        // Set up top mentors RecyclerView
        bookedSessionsRecyclerView = findViewById(R.id.bookedSessionsRecycler)
        bookedSessionsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Choose the layout resource ID based on the card type (horizontal or vertical)
        val horizontalLayoutResourceId = R.layout.mentorcard
        val verticalLayoutResourceId = R.layout.verticalmentorcards

        // Initialize the adapter with the chosen layout resource ID
        bookedSessionsAdapter = MentorCardAdapter(this, topMentors, verticalLayoutResourceId)
        bookedSessionsRecyclerView.adapter = bookedSessionsAdapter
    }
}