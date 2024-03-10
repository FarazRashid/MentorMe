package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.muhammadfarazrashid.i2106595.Mentor.OnImageUrlListener
import com.squareup.picasso.Picasso

class aboutMentorPage : AppCompatActivity() {

    private lateinit var mentorName: TextView
    private lateinit var mentorPosition: TextView
    private lateinit var aboutMe: TextView
    private lateinit var mentorImageView: ImageView
    private lateinit var currentMentor: Mentor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aboutmentorpage)

        // Retrieve Mentor object from intent extras
        val mentor = intent.getParcelableExtra<Mentor>("mentor")

        // Log mentor details to verify if data is received correctly
        mentor?.let {
            with(it) {
                logMentorDetails(id, name, position, availability, salary, description, isFavorite)
            }
        }

        // Initialize views
        initViews()

        // Set mentor details
        mentor?.let { setMentorDetails(it) }


        // Set up onClickListeners
        setUpOnClickListeners()
    }

    private fun initViews() {
        mentorName = findViewById(R.id.mentorName)
        mentorPosition = findViewById(R.id.position)
        aboutMe = findViewById(R.id.aboutMe)
        mentorImageView = findViewById(R.id.imageView9)
    }

    private fun setMentorDetails(mentor: Mentor) {
        mentorName.text = mentor.name
        mentorPosition.text = mentor.position
        aboutMe.text = mentor.description
        currentMentor = mentor

        // Call getImageUrl function to get the mentor's image URL
        Mentor.getImageUrl(mentor.id, object : OnImageUrlListener {
            override fun onSuccess(imageUrl: String) {
                // Load image using Picasso
                Picasso.get().load(imageUrl).into(mentorImageView)
            }

            override fun onFailure(errorMessage: String) {
                // Handle failure to retrieve image URL
                Log.e("MentorCardAdapter", "Failed to retrieve image URL: $errorMessage")
            }
        })
    }

    private fun Mentor.logMentorDetails(
        id: String?,
        name: String?,
        position: String?,
        availability: String?,
        salary: String?,
        description: String?,
        isFavorite: Boolean
    ) {
        id?.let { Log.d("AboutMentorPage", "Mentor ID: $it") }
        name?.let { Log.d("AboutMentorPage", "Mentor Name: $it") }
        position?.let { Log.d("AboutMentorPage", "Mentor Position: $it") }
        availability?.let { Log.d("AboutMentorPage", "Mentor Availability: $it") }
        salary?.let { Log.d("AboutMentorPage", "Mentor Salary: $it") }
        description?.let { Log.d("AboutMentorPage", "Mentor Description: $it") }
        Log.d("AboutMentorPage", "Mentor isFavorite: $isFavorite")
    }

    private fun checkIfCommunityChatExists(callback: (Boolean) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val chatRef = database.getReference("chat/community_chats/${currentMentor.id}/users")
        chatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                callback(dataSnapshot.exists())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                Log.e("CalendarPage", "Error fetching chat data: ${databaseError.message}")
                callback(false)
            }
        })
    }

    private fun isAlreadyRegisteredForChat(callback: (Boolean) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val currentUser = UserManager.getCurrentUser()
        val userChatRef = database.getReference("users/${currentUser?.id}/chats/community_chats")
        userChatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var isRegistered = false
                    for (snapshot in dataSnapshot.children) {
                        val mentorId = snapshot.value as String
                        if (mentorId == currentMentor.id) {
                            isRegistered = true
                            Log.d("CalendarPage", "Is already registered for chat: $isRegistered")
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

    private fun registerForCommunityChat() {
        val database = FirebaseDatabase.getInstance()
        val chatRef = database.getReference("chat").push()
        val chatKey = chatRef.key
        val currentUser = UserManager.getCurrentUser()

        // Save chat reference under user's chats
        currentUser?.let { user ->
            val userChatRef = database.getReference("users/${user.id}/chats/community_chats")
            userChatRef.push().setValue(currentMentor.id)
        }

        // Optionally, save chat details under the chat node
        chatKey?.let { key ->
            val chatDetailsRef = database.getReference("chat/community_chats/${currentMentor.id}/users")
            currentUser?.id?.let { userId ->
                chatDetailsRef.push().setValue(userId)
            }
        }
    }

    private fun addUserToCommunityChat() {
        val database = FirebaseDatabase.getInstance()
        val chatRef = database.getReference("chat/community_chats/${currentMentor.id}/users")
        val currentUser = UserManager.getCurrentUser()
        currentUser?.let { user ->
            chatRef.push().setValue(user.id)
        }

        currentUser?.let { user ->
            val userChatRef = database.getReference("users/${user.id}/chats/community_chats")
            userChatRef.push().setValue(currentMentor.id)
        }
    }

    private fun navigateToMentorReviewPage(mentor: Mentor) {
        val intent = Intent(this, reviewpage::class.java)
        intent.putExtra("mentor", mentor)
        startActivity(intent)
    }

    private fun navigateToCommunityChatPage(mentor: Mentor) {
        val intent = Intent(this, communityChatActivity::class.java)
        checkIfCommunityChatExists { communityChatExists ->
            if (communityChatExists) {
                // Community chat exists, check if user is registered
                isAlreadyRegisteredForChat { isRegistered ->
                    if (!isRegistered) {
                        addUserToCommunityChat()
                        Log.d("CalendarPage", "User is now registered for community chat")
                    }
                    // Proceed to community chat activity
                    intent.putExtra("mentor", mentor)
                    startActivity(intent)
                }
            } else {
                // Community chat doesn't exist, create and register the user
                registerForCommunityChat()
                Log.d("CalendarPage", "Created a new community chat and registered user")
                intent.putExtra("mentor", mentor)
                startActivity(intent)
            }
        }
    }

    private fun navigateToSignUpPage(mentor: Mentor) {
        val intent = Intent(this, calendarPage::class.java)
        intent.putExtra("mentor", mentor)
        startActivity(intent)
    }

    private fun setUpOnClickListeners() {
        findViewById<ImageView>(R.id.imageView4).setOnClickListener { onBackPressed() }
        findViewById<Button>(R.id.reviewButton).setOnClickListener {
            navigateToMentorReviewPage(currentMentor)
        }
        findViewById<Button>(R.id.communityButton).setOnClickListener {
            navigateToCommunityChatPage(currentMentor)
        }
        findViewById<Button>(R.id.signupButton).setOnClickListener {
            navigateToSignUpPage(currentMentor)
        }
    }
}
