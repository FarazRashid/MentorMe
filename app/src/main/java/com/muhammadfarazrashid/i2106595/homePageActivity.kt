package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class homePageActivity : AppCompatActivity() {

    private lateinit var topMentorsRecycler: RecyclerView
    private lateinit var recentMentorsRecycler: RecyclerView
    private lateinit var educationMentorsRecycler: RecyclerView
    private lateinit var badgesRecycler: RecyclerView

    private val topMentors = ArrayList<Mentor>()
    private val recentMentors = ArrayList<Mentor>()
    private val educationMentors = ArrayList<Mentor>()
    private val badges = ArrayList<Badge>()

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        initViews()
        initMentors()
        initBadges()
        initBottomNavigation()
        fetchAllMentors()

        val addMentor = findViewById<ImageView>(R.id.addMentorButton)
        addMentor.setOnClickListener {
            startActivity(Intent(this, AddAMentor::class.java))
        }

        val notifications = findViewById<ImageView>(R.id.bellIcon)
        notifications.setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }
    }


    private fun initViews() {
        topMentorsRecycler = findViewById(R.id.topMentorsRecycler)
        recentMentorsRecycler = findViewById(R.id.recentMentorsRecycler)
        educationMentorsRecycler = findViewById(R.id.educationMentorsRecycler)
        badgesRecycler = findViewById(R.id.badgesRecycler)
    }

    private fun fetchAllMentors() {
        database.getReference("Mentors").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val mentors = mutableListOf<Mentor>()
                // Iterate through all child nodes under "Mentors"
                snapshot.children.forEach { mentorSnapshot ->
                    val mentor = mentorSnapshot.getValue(Mentor::class.java)
                    mentor?.let {
                        // Set the id for each mentor based on the key of the child node
                        it.id = mentorSnapshot.key
                        mentors.add(it)
                    }
                }
                // Populate mentors RecyclerViews with the fetched mentors
                setupMentorsRecycler(topMentorsRecycler, mentors)
                setupMentorsRecycler(recentMentorsRecycler, mentors)
                setupMentorsRecycler(educationMentorsRecycler, mentors)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("homePageActivity", "Failed to fetch mentors: ${error.message}")
            }
        })
    }




    private fun initMentors() {
        // Initialize top mentors

        // Setup RecyclerViews
        setupMentorsRecycler(topMentorsRecycler, topMentors)
        setupMentorsRecycler(recentMentorsRecycler, recentMentors)
        setupMentorsRecycler(educationMentorsRecycler, educationMentors)
    }

    private fun setupMentorsRecycler(recyclerView: RecyclerView, mentors: List<Mentor>) {
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MentorCardAdapter(this, mentors, R.layout.mentorcard)
    }

    private fun initBadges() {
        badges.add(Badge("Category 1", false))
        badges.add(Badge("Category 2", false))
        badges.add(Badge("Category 3", true)) // This badge is selected
        badges.add(Badge("Category 4", false))
        badges.add(Badge("Category 5", false))

        badgesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        badgesRecycler.adapter = BadgeAdapter(badges).apply {
            setOnBadgeClickListener(object : BadgeAdapter.OnBadgeClickListener {
                override fun onBadgeClick(position: Int) {
                    Log.d("MainActivity", "Badge clicked at position: $position")
                }
            })
        }
    }

    private fun initBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menu_search -> startActivity(Intent(this, searchPageActivity::class.java))
                R.id.menu_home -> startActivity(Intent(this, homePageActivity::class.java))
                R.id.menu_chat -> startActivity(Intent(this, mainChatActivity::class.java))
                R.id.menu_profile -> startActivity(Intent(this, MyProfileActivity::class.java))
            }
        }
    }
}
