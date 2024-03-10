package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MentorChatActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mentorchat)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.communityChatRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        val chatMessages = ArrayList<ChatMessage>()
        chatAdapter = ChatAdapter(chatMessages)
        addExampleMessages()
        recyclerView.adapter = chatAdapter

        setButtonClickListeners()
        setBottomNavigationListener()
        setAddMentorClickListener()
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

    private fun addExampleMessages() {
        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        val fourthPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)

        chatAdapter.apply {
            addMessage(ChatMessage("Hello!", "10:00 AM", false, otherPersonImage))
            addMessage(ChatMessage("How are you?", "10:05 AM", false, otherPersonImage))
            addMessage(ChatMessage("Hi there!", "10:10 AM", true, fourthPersonImage))
            addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, fourthPersonImage))
            addMessage(ChatMessage("Hello!", "10:00 AM", false, fourthPersonImage))
            addMessage(ChatMessage("How are you?", "10:05 AM", false, fourthPersonImage))
            addMessage(ChatMessage("Hi there!", "10:10 AM", true, fourthPersonImage))
            addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, fourthPersonImage))
        }
    }
}
