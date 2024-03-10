package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class mainChatActivity : AppCompatActivity() {

    private lateinit var allMessagesRecyclerView: RecyclerView
    private lateinit var allMessagesAdapter: AllMessagesAdapter
    private lateinit var mentorRecyclerView: RecyclerView
    private lateinit var mentorAdapter: MentorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainchatpage)

        initializeBottomNavigationListener()
        initializeAddMentorButton()

        initializeAllMessagesRecyclerView()
        initializeMentorRecyclerView()

        initializeBackButton()
    }

    private fun initializeBottomNavigationListener() {
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

    private fun initializeAddMentorButton() {
        findViewById<ImageView>(R.id.addMentorButton).setOnClickListener {
            startActivity(Intent(this, AddAMentor::class.java))
        }
    }

    private fun initializeAllMessagesRecyclerView() {
        allMessagesRecyclerView = findViewById<RecyclerView>(R.id.allMessagesRecycler)
        allMessagesRecyclerView.layoutManager = LinearLayoutManager(this)

        val chatMessages = mutableListOf<AllMessagesChat>()

        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        val elizabeth: Drawable? = ContextCompat.getDrawable(this, R.mipmap.elizabeth)
        val bob: Drawable? = ContextCompat.getDrawable(this, R.mipmap.bob)
        val emily: Drawable? = ContextCompat.getDrawable(this, R.mipmap.jane)
        val emilybrown: Drawable? = ContextCompat.getDrawable(this, R.mipmap.emilybrown)

        otherPersonImage?.let {
            chatMessages.add(AllMessagesChat("John Cooper", 0, it))
            chatMessages.add(AllMessagesChat("Elizabeth", 5, elizabeth))
            chatMessages.add(AllMessagesChat("Bob", 3, bob))
            chatMessages.add(AllMessagesChat("Emily", 0, emily))
            chatMessages.add(AllMessagesChat("Emily Brown", 1, emilybrown))
        }

        allMessagesAdapter = AllMessagesAdapter(chatMessages)
        allMessagesRecyclerView.adapter = allMessagesAdapter
    }

    private fun initializeMentorRecyclerView() {
        mentorRecyclerView = findViewById<RecyclerView>(R.id.communityRecyclerView)
        mentorRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val mentorItems = mutableListOf<MentorItem>()

        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        val elizabeth: Drawable? = ContextCompat.getDrawable(this, R.mipmap.elizabeth)
        val bob: Drawable? = ContextCompat.getDrawable(this, R.mipmap.bob)
        val emily: Drawable? = ContextCompat.getDrawable(this, R.mipmap.jane)
        val emilybrown: Drawable? = ContextCompat.getDrawable(this, R.mipmap.emilybrown)

        otherPersonImage?.let {
            mentorItems.add(MentorItem(it, true))
            mentorItems.add(MentorItem(elizabeth, false))
            mentorItems.add(MentorItem(bob, true))
            mentorItems.add(MentorItem(emily, false))
            mentorItems.add(MentorItem(emilybrown, true))
        }

        mentorAdapter = MentorAdapter(mentorItems)
        mentorRecyclerView.adapter = mentorAdapter
    }

    private fun initializeBackButton() {
        findViewById<ImageView>(R.id.imageView10).setOnClickListener {
            onBackPressed()
        }
    }
}
