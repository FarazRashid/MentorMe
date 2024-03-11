package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
    private lateinit var sendButton: Button
    private lateinit var micButton: Button
    private lateinit var messageField: EditText
    private lateinit var takePhoto: Button
    private lateinit var sendImage: Button
    private lateinit var attachImage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.communitychat)

        recyclerView = findViewById(R.id.communityChatRecyclerView)

        currentMentor = intent.getParcelableExtra<Mentor>("mentor")!!
        initViews()
        setMentorDetails(currentMentor)
        setButtonClickListeners()
        setBottomNavigationListener()
        setAddMentorClickListener()
    }

    private fun setMentorDetails(mentor: Mentor) {
        mentorName = findViewById(R.id.mentorName)
        mentorName.text = currentMentor.name
        mentorImage = findViewById(R.id.imageView9)

        if (!mentor.getprofilePictureUrl().isEmpty()) {
            Picasso.get().load(mentor.getprofilePictureUrl()).into(mentorImage)
            addExampleMessages(mentor.getprofilePictureUrl())
        }
    }

    private fun initViews() {
        micButton = findViewById(R.id.micButton)
        messageField = findViewById(R.id.reviewText)
        takePhoto = findViewById(R.id.takePhoto)
        sendImage = findViewById(R.id.sendImage)
        attachImage = findViewById(R.id.sendFile)
        sendButton = findViewById(R.id.sendButton)

        recyclerView = findViewById(R.id.communityChatRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        val chatMessages = ArrayList<ChatMessage>()
        chatAdapter = ChatAdapter(chatMessages)
        recyclerView.adapter = chatAdapter
    }

    private fun sendMessage() {
        val message = messageField.text.toString()
        //get current time in hour and minute e.g. 10:20 AM
        val currentTime = java.text.SimpleDateFormat("HH:mm a").format(java.util.Date())
        chatAdapter.addMessage(ChatMessage(message, currentTime, true, ""))
    }

    private fun setButtonClickListeners() {

        sendButton.setOnClickListener {
            val message = messageField.text.toString()
            if (message.isNotEmpty()) {
                sendMessage()
                messageField.text.clear()
            }
        }

        micButton.setOnClickListener {
            // recordVoiceMessage()
        }

        takePhoto.setOnClickListener {
            //takePhoto()
        }

        sendImage.setOnClickListener {
            //sendImage()
        }

        attachImage.setOnClickListener {
            //attachImage()
        }


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


}
