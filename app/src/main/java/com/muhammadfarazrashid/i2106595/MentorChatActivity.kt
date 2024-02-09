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

class MentorChatActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mentorchat)

        recyclerView = findViewById(R.id.communityChatRecyclerView)

        // Initialize RecyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager


        val chatMessages = ArrayList<ChatMessage>()

        chatAdapter = ChatAdapter(chatMessages)
        addExampleMessages()

        recyclerView.adapter = chatAdapter

        //click on phone button and go to phone page

        val phone = findViewById<Button>(R.id.callButton)

        phone.setOnClickListener {
            val intent = Intent(this, PhoneCallActivity::class.java)
            startActivity(intent)
        }

        //click on videobutton and go to videocall page

        val video = findViewById<Button>(R.id.videoButton)

        video.setOnClickListener {
            val intent = Intent(this, VideoCallActivity::class.java)
            startActivity(intent)
        }

        //click on imageview4 and go back

        val bottomNavigationView = findViewById<Button>(R.id.backbutton)

        bottomNavigationView.setOnClickListener {
            onBackPressed()
        }

        //click on takephoto button inside of linear layout and go to photo page

        val linearLayout: LinearLayout = findViewById(R.id.linearLayout2)
        val takePhotoButton: Button = linearLayout.findViewById(R.id.takePhoto)

        // Set click listener on the Take Photo button
        takePhotoButton.setOnClickListener {
            // Start PhotoActivity when the button is clicked
            val intent = Intent(this, PhotoActivity::class.java)
            startActivity(intent)
        }

    }



    private fun addExampleMessages() {
        // Example messages from the other user
        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        chatAdapter.addMessage(ChatMessage("Hello!", "10:00 AM", false, otherPersonImage))
        chatAdapter.addMessage(ChatMessage("How are you?", "10:05 AM", false, otherPersonImage))

        // Example messages from the user
        chatAdapter.addMessage(ChatMessage("Hi there!", "10:10 AM", true, otherPersonImage))
        chatAdapter.addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, otherPersonImage))


        val fourthPerson : Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        chatAdapter.addMessage(ChatMessage("Hello!", "10:00 AM", false, fourthPerson))
        chatAdapter.addMessage(ChatMessage("How are you?", "10:05 AM", false, fourthPerson))

        chatAdapter.addMessage(ChatMessage("Hi there!", "10:10 AM", true, fourthPerson))
        chatAdapter.addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, fourthPerson))



    }

}