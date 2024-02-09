package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookedSessionsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SessionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_sessions)

        recyclerView = findViewById(R.id.bookedSessionsRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val otherPersonImage: Drawable? = ContextCompat.getDrawable(this, R.mipmap.johnmayer)
        val elizabeth: Drawable? = ContextCompat.getDrawable(this, R.mipmap.elizabeth)
        val bob: Drawable? = ContextCompat.getDrawable(this, R.mipmap.bob)
        val emily: Drawable? = ContextCompat.getDrawable(this, R.mipmap.jane)
        val emilybrown: Drawable? = ContextCompat.getDrawable(this, R.mipmap.emilybrown)

        // Sample session data
        val sessions = listOf(
            Session(otherPersonImage, "Session 1", "2024-02-10", "10:00 AM", "Position 1"),
            Session(elizabeth, "Session 2", "2024-02-11", "11:00 AM", "Position 2"),
            Session(bob, "Session 3", "2024-02-12", "12:00 PM", "Position 3")
            // Add more sessions as needed
        )

        adapter = SessionAdapter(sessions)
        recyclerView.adapter = adapter

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menu_search -> {
                    val intent = Intent(this, searchPageActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_home -> {
                    val intent = Intent(this, homePageActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_chat -> {
                    val intent = Intent(this, mainChatActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_profile -> {
                    val intent = Intent(this, MyProfileActivity::class.java)
                    startActivity(intent)
                }

            }

        }

        //click on add mentor button and go to add mentor page
        val addMentor = findViewById<ImageView>(R.id.addMentorButton)
        addMentor.setOnClickListener {
            val intent = Intent(this, AddAMentor::class.java)
            startActivity(intent)
        }

        //click on imageview10 and go back

        val imageView10 = findViewById<ImageView>(R.id.imageView10)
        imageView10.setOnClickListener {
            onBackPressed()
        }

    }
}