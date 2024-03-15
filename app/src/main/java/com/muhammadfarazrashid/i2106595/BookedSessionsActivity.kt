package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BookedSessionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SessionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_sessions)

        recyclerView = findViewById(R.id.bookedSessionsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        setupSessions()
        setupBottomNavigation()
        setupAddMentorButton()
        setupBackButton()
    }

    //call the firebase database and fetch all the bookings underneath the user's id with booking date, time, and id of the mentor
    private fun setupSessions() {
        val myDatabase= UserManager.getCurrentUser()
            ?.let { FirebaseDatabase.getInstance().getReference("users").child(it.id) }
        val myRef = myDatabase?.child("bookings")

        val listOfIdsOfMentors = mutableListOf<String>()

        if (myRef != null) {
            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val sessions = mutableListOf<Session>()
                    for (snapshot in dataSnapshot.children) {
                        val bookingDate= snapshot.child("bookingDate").getValue(String::class.java)
                        val bookingTime= snapshot.child("bookingTime").getValue(String::class.java)
                        val mentorId= snapshot.child("mentorId").getValue(String::class.java)
                        var session: Session = Session(bookingDate, bookingTime, mentorId)
                        listOfIdsOfMentors.add(session.mentorId)
                        sessions.add(session)
                    }
                    setupMentors(sessions, listOfIdsOfMentors)

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }
    }

    private fun setupMentors(sessions:MutableList<Session>,listOfIdsOfMentors: MutableList<String>) {
        val myDatabase= FirebaseDatabase.getInstance().getReference("Mentors")

        myDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val mentors = mutableListOf<Mentor>()
                for (snapshot in dataSnapshot.children) {
                    if (listOfIdsOfMentors.contains(snapshot.key)) {
                        val mentor = snapshot.getValue(Mentor::class.java)
                        if (mentor != null) {
                            //get the session with the mentor id and add the mentor to the session
                            //find index of session with that mentor id and then add to that
                            val index = listOfIdsOfMentors.indexOf(snapshot.key)
                            sessions[index].mentor = mentor
                        }
                    }
                }
                setupAdapter(sessions)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

    }

    private fun setupAdapter(sessions: MutableList<Session>) {
        adapter = SessionAdapter(sessions)
        recyclerView.adapter = adapter
    }

    private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            val intent = when (item.itemId) {
                R.id.menu_search -> Intent(this, searchPageActivity::class.java)
                R.id.menu_home -> Intent(this, homePageActivity::class.java)
                R.id.menu_chat -> Intent(this, mainChatActivity::class.java)
                R.id.menu_profile -> Intent(this, MyProfileActivity::class.java)
                else -> null
            }
            intent?.let {
                startActivity(it)
            }
        }
    }

    private fun setupAddMentorButton() {
        val addMentor = findViewById<ImageView>(R.id.addMentorButton)
        addMentor.setOnClickListener {
            val intent = Intent(this, AddAMentor::class.java)
            startActivity(intent)
        }
    }

    private fun setupBackButton() {
        val imageView10 = findViewById<ImageView>(R.id.imageView10)
        imageView10.setOnClickListener {
            onBackPressed()
        }
    }
}
