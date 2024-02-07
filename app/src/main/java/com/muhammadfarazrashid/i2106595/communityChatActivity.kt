package com.muhammadfarazrashid.i2106595

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class communityChatActivity: AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.communitychat)

        recyclerView = findViewById(R.id.communityChatRecyclerView)

        // Initialize RecyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager


        val chatMessages = ArrayList<ChatMessage>()

        chatAdapter = ChatAdapter(chatMessages)
        addExampleMessages()

        recyclerView.adapter = chatAdapter
    }

    private fun addExampleMessages() {
        // Example messages from the other user
        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        chatAdapter.addMessage(ChatMessage("Hello!", "10:00 AM", false, otherPersonImage))
        chatAdapter.addMessage(ChatMessage("How are you?", "10:05 AM", false, otherPersonImage))

        // Example messages from the user
        chatAdapter.addMessage(ChatMessage("Hi there!", "10:10 AM", true, otherPersonImage))
        chatAdapter.addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, otherPersonImage))

        val thirdPerson : Drawable? = ContextCompat.getDrawable(this, R.mipmap.elizabeth)
        chatAdapter.addMessage(ChatMessage("Hello!", "10:00 AM", false, thirdPerson))
        chatAdapter.addMessage(ChatMessage("My name is elizabeth", "10:05 AM", false, thirdPerson))

        chatAdapter.addMessage(ChatMessage("Hi there!", "10:10 AM", true, thirdPerson))
        chatAdapter.addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, thirdPerson))

        val fourthPerson : Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        chatAdapter.addMessage(ChatMessage("Hello!", "10:00 AM", false, fourthPerson))
        chatAdapter.addMessage(ChatMessage("How are you?", "10:05 AM", false, fourthPerson))

        chatAdapter.addMessage(ChatMessage("Hi there!", "10:10 AM", true, fourthPerson))
        chatAdapter.addMessage(ChatMessage("I'm doing well, thanks!", "10:15 AM", true, fourthPerson))





    }
}