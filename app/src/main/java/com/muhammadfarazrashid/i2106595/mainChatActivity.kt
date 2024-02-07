package com.muhammadfarazrashid.i2106595

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class mainChatActivity : AppCompatActivity() {

    private var allMessagesRecyclerView: RecyclerView? = null
    private var allMessagesAdapter: AllMessagesAdapter? = null

    private var mentorRecyclerView: RecyclerView? = null
    private var mentorAdapter: MentorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainchatpage)

        // Initialize RecyclerView
        allMessagesRecyclerView = findViewById<RecyclerView>(R.id.allMessagesRecycler)
        allMessagesRecyclerView?.layoutManager = LinearLayoutManager(this)

        // Create list of chat messages
        val chatMessages: MutableList<AllMessagesChat> = ArrayList()

        // Example messages
        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        val elizabeth: Drawable? = ContextCompat.getDrawable(this, R.mipmap.elizabeth)
        val bob: Drawable? = ContextCompat.getDrawable(this, R.mipmap.bob)
        val emily: Drawable? = ContextCompat.getDrawable(this, R.mipmap.jane)
        val emilybrown: Drawable? = ContextCompat.getDrawable(this, R.mipmap.emilybrown)
        if (otherPersonImage != null) {
            chatMessages.add(AllMessagesChat("John Cooper", 0, otherPersonImage))
            chatMessages.add(AllMessagesChat("Elizabeth", 5, elizabeth))
            chatMessages.add(AllMessagesChat("Bob", 3, bob))
            chatMessages.add(AllMessagesChat("Emily", 0, emily))
            chatMessages.add(AllMessagesChat("Emily Brown", 1, emilybrown))

        }

        // Initialize adapter with the list of chat messages
        allMessagesAdapter = AllMessagesAdapter(chatMessages)
        allMessagesRecyclerView?.adapter = allMessagesAdapter


        allMessagesAdapter = AllMessagesAdapter(chatMessages)
        allMessagesRecyclerView?.adapter = allMessagesAdapter

        // Initialize RecyclerView for mentors
        mentorRecyclerView = findViewById<RecyclerView>(R.id.communityRecyclerView)
        mentorRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Create list of mentor items
        val mentorItems: MutableList<MentorItem> = ArrayList()


        mentorItems.add(MentorItem(otherPersonImage, true))
        mentorItems.add(MentorItem(elizabeth, false))
        mentorItems.add(MentorItem(bob , true))
        mentorItems.add(MentorItem(emily, false))
        mentorItems.add(MentorItem(emilybrown, true))


        // Initialize adapter for mentors with the list of mentor items
        mentorAdapter = MentorAdapter(mentorItems)
        mentorRecyclerView?.adapter = mentorAdapter

        //click back button go back to last activity
        val backButton = findViewById<ImageView>(R.id.imageView10)
        backButton.setOnClickListener {
            onBackPressed()
        }

        //click on all messages recycler and go to chat activity





    }

}
