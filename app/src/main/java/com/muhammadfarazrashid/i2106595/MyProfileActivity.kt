package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MyProfileActivity : AppCompatActivity() {

    private lateinit var reviewAdapter: ReviewItemAdapter
    private lateinit var topMentorsRecycler: RecyclerView
    private lateinit var topMentors: ArrayList<Mentor>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        // Retrieve the RecyclerView using its ID
        val myReviewsRecycler = findViewById<RecyclerView>(R.id.myReviewsRecycler)
        myReviewsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Sample data for reviews
        val reviewList = getSampleReviewData()

        // Initialize the adapter
        reviewAdapter = ReviewItemAdapter(reviewList)
        myReviewsRecycler.adapter = reviewAdapter

        topMentorsRecycler = findViewById(R.id.topMentorsRecycler)
        topMentorsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topMentors = ArrayList()
        topMentors.add(Mentor("Faraz Rashid", "Android Developer", "available", "$5000/hr"))
        topMentors.add(Mentor("John Doe", "UX Designer", "unavailable", "$7000/hr"))
        topMentors.add(Mentor("Jane Doe", "UI Designer", "available", "$6000/hr"))
        topMentors.add(Mentor("John Smith", "Web Developer", "unavailable", "$8000/hr"))
        topMentors.add(Mentor("Jane Smith", "Web Developer", "available", "$9000/hr"))

        val horizontalLayoutResourceId = R.layout.mentorcard
        val topMentorsAdapter = MentorCardAdapter(this, topMentors, horizontalLayoutResourceId)
        topMentorsRecycler.adapter = topMentorsAdapter


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

        //click on bookedsessions and go to bookedsessions page

        val bookedSessions = findViewById<View>(R.id.bookedSessions)
        bookedSessions.setOnClickListener {
            val intent = Intent(this, BookedSessionsActivity::class.java)
            startActivity(intent)
        }

        //click on edit profile image and go to editprofile page

        val editProfile = findViewById<View>(R.id.editProfile)

        editProfile.setOnClickListener {
            val intent = Intent(this, EditProfilePageActivity::class.java)
            startActivity(intent)
        }

        //click on back button and go back

        val backButton = findViewById<Button>(R.id.backbutton)
        backButton.setOnClickListener {
            onBackPressed()
        }

    }

    private fun getSampleReviewData(): List<ReviewItem> {
        val reviewList = ArrayList<ReviewItem>()

        // Add sample reviews
        reviewList.add(ReviewItem("John Cooper", "John provided excellent prototyping techniques and insights. I highly recommend him!", 5))
        reviewList.add(ReviewItem("Alice Smith", "Great mentor, very knowledgeable in app development.", 4))
        reviewList.add(ReviewItem("Emma Johnson", "Average mentor, could improve communication skills.", 3))

        return reviewList
    }
}