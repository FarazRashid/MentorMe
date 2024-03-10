package com.muhammadfarazrashid.i2106595


import UserManager
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.childEvents
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

    private fun isAlreadyRegisteredForChat(callback: (Boolean) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val currentUser = UserManager.getCurrentUser()
        val userChatRef = database.getReference("users/${currentUser?.id}/chats/mentor_chats")
        userChatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var isRegistered = false
                    for (snapshot in dataSnapshot.children) {
                        val mentorId = snapshot.value as String
                        if (mentorId == currentMentor.id) {
                            Log.d("CalendarPage", "Is already registered for chat: $isRegistered")
                            isRegistered = true
                            break
                        }
                    }
                    Log.d("CalendarPage", "Is already registered for chat: $isRegistered")
                    callback(isRegistered)
                } else {
                    callback(false)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                Log.e("CalendarPage", "Error fetching user chat data: ${databaseError.message}")
                callback(false)
            }
        })
    }


    private fun registerForMentorChat() {
        val database = FirebaseDatabase.getInstance()
        val chatRef = database.getReference("chat").push()
        val chatKey = chatRef.key
        val currentUser = UserManager.getCurrentUser()

        // Save chat reference under user's chats
        currentUser?.let { user ->
            val userChatRef = database.getReference("users/${user.id}/chats/mentor_chats")
            chatKey?.let { key ->
                userChatRef.child(key).setValue(currentMentor.id)
            }
        }

        // Save chat reference under mentor's chats
        val mentorChatRef = database.getReference("Mentors/${currentMentor.id}/chats/mentor_chats")
        chatKey?.let { key ->
            mentorChatRef.child(key).setValue(currentUser?.id)
        }

        // Optionally, save chat details under the chat node
        chatKey?.let { key ->
            val chatDetailsRef = database.getReference("chat/mentor_chats/$key/details")
            chatDetailsRef.child("mentor_id").setValue(currentMentor.id)
            currentUser?.id?.let { userId ->
                chatDetailsRef.child("user_id").setValue(userId)
            }
        }
    }

    private fun navigateToChatPage(mentor: Mentor) {
        val intent = Intent(this, MentorChatActivity::class.java)
        isAlreadyRegisteredForChat { isRegistered ->
            if (isRegistered) {
                // User is already registered for chat with the current mentor
                Log.d("CalendarPage", "User is already registered for chat with mentor")
            } else {
                // Register the user for chat with the current mentor
                registerForMentorChat()
                Log.d("CalendarPage", "User is now registered for chat with mentor")
            }
            intent.putExtra("mentor", mentor)
            startActivity(intent)
        }
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
