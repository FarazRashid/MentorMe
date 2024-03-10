package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class communityChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var mentorName: TextView
    private lateinit var currentMentor: Mentor
    private lateinit var mentorImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.communitychat)

        recyclerView = findViewById(R.id.communityChatRecyclerView)

        currentMentor = intent.getParcelableExtra<Mentor>("mentor")!!
        setMentorDetails(currentMentor)
        initViews()

        setButtonClickListeners()
        setBottomNavigationListener()
        setAddMentorClickListener()
    }

    private fun setMentorDetails(mentor: Mentor) {
        mentorName = findViewById(R.id.mentorName)
        mentorName.text = currentMentor.name
        mentorImage = findViewById(R.id.imageView9)

        Mentor.getImageUrl(mentor.id, object : Mentor.OnImageUrlListener {
            override fun onSuccess(imageUrl: String) {
                // Load image using Picasso
                Picasso.get().load(imageUrl).fetch(object: com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        Picasso.get().load(imageUrl).into(mentorImage)
                        addExampleMessages(imageUrl)
                    }

                    override fun onError(e: Exception?) {
                        // Handle failure to load image
                        Log.e("MentorChatActivity", "Failed to load mentor image: ${e?.message}")
                    }
                })
            }

            override fun onFailure(errorMessage: String) {
                // Handle failure to retrieve image URL
                Log.e("MentorChatActivity", "Failed to retrieve image URL: $errorMessage")
            }
        })
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.communityChatRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        val chatMessages = ArrayList<ChatMessage>()
        chatAdapter = ChatAdapter(chatMessages)
        recyclerView.adapter = chatAdapter
    }

    private fun setButtonClickListeners() {
        findViewById<Button>(R.id.callButton).setOnClickListener {
            startActivity(Intent(this, PhoneCallActivity::class.java))
        }

        findViewById<Button>(R.id.videoButton).setOnClickListener {
            startActivity(Intent(this, VideoCallActivity::class.java))
        }

        findViewById<Button>(R.id.backbutton).setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.takePhoto).setOnClickListener {
            startActivity(Intent(this, PhotoActivity::class.java))
        }
    }

    private fun setBottomNavigationListener() {
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

    private fun setAddMentorClickListener() {
        findViewById<ImageView>(R.id.addMentorButton).setOnClickListener {
            startActivity(Intent(this, AddAMentor::class.java))
        }
    }

    private fun addExampleMessages(mentorImageUrl:String) {
        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        val thirdPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.elizabeth)
        val fourthPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)

        chatAdapter.apply {
            // Example messages from the other user
            chatAdapter.apply {
                addMessage(ChatMessage("Hello!", "10:00 AM", false, mentorImageUrl))
                addMessage(ChatMessage("How are you?", "10:05 AM", false, mentorImageUrl))
                addMessage(ChatMessage("Hi there!", "10:10 AM", true, mentorImageUrl))
                addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, mentorImageUrl))
                addMessage(ChatMessage("Hello!", "10:00 AM", false, mentorImageUrl))
                addMessage(ChatMessage("How are you?", "10:05 AM", false, mentorImageUrl))
                addMessage(ChatMessage("Hi there!", "10:10 AM", true, mentorImageUrl))
                addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, mentorImageUrl))
            }
        }
    }
//        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
//        val thirdPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.elizabeth)
//        val fourthPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
//
//        chatAdapter.apply {
//            // Example messages from the other user
//            addMessage(ChatMessage("Hello!", "10:00 AM", false, otherPersonImage))
//            addMessage(ChatMessage("How are you?", "10:05 AM", false, otherPersonImage))
//
//            // Example messages from the user
//            addMessage(ChatMessage("Hi there!", "10:10 AM", true, otherPersonImage))
//            addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, otherPersonImage))
//
//            addMessage(ChatMessage("Hello!", "10:00 AM", false, thirdPersonImage))
//            addMessage(ChatMessage("My name is Elizabeth", "10:05 AM", false, thirdPersonImage))
//            addMessage(ChatMessage("Hi there!", "10:10 AM", true, thirdPersonImage))
//            addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, thirdPersonImage))
//
//            addMessage(ChatMessage("Hello!", "10:00 AM", false, fourthPersonImage))
//            addMessage(ChatMessage("How are you?", "10:05 AM", false, fourthPersonImage))
//            addMessage(ChatMessage("Hi there!", "10:10 AM", true, fourthPersonImage))
//            addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, fourthPersonImage))
//        }

}
