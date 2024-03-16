package com.muhammadfarazrashid.i2106595

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class searchResultsActivity : AppCompatActivity() {

    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var searchResultsAdapter: MentorCardAdapter
    private lateinit var topMentors: ArrayList<Mentor>
    private lateinit var originalTopMentors: ArrayList<Mentor>
    private var searchQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchresults)

        //get intent data
        val intent = intent
        searchQuery = intent.getStringExtra("search_query")
        Log.d("searchResultsActivity", "Search query: $searchQuery")
        fetchUserFavorites()
        setupFilterSpinner()
        setupBottomNavigation()
        setupAddMentorButton()
        setupBackButton()
        setupCardClickListener()
    }

    private fun fetchUserFavorites() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val favoritesRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("favorites")
        favoritesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteMentorIds = mutableListOf<String>()
                for (mentorSnapshot in snapshot.children) {
                    val mentorId = mentorSnapshot.key
                    mentorId?.let { favoriteMentorIds.add(it) }
                    Log.d(MotionEffect.TAG, "Fetched favorite mentor ID: $mentorId")
                }
                // After fetching favorite mentor IDs, fetch mentor objects
                initializeTopMentors(favoriteMentorIds)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(MotionEffect.TAG, "Failed to fetch user favorites: " + error.message)
            }
        })
    }

    //fetch mentors that have the search query in their name or position
    private fun initializeTopMentors(favoriteMentorIds: List<String>) {
        var database = FirebaseDatabase.getInstance()
        var myRef = database.getReference("Mentors")

        myRef.get().addOnSuccessListener {
            if (it.exists()) {
                val mentorList = it.children
                topMentors = ArrayList()
                originalTopMentors = ArrayList()
                for (mentor in mentorList) {
                    val mentorData = mentor.getValue(Mentor::class.java)
                    if (mentorData != null) {
                        val mentorKey = mentor.key // Get the key of the DataSnapshot
                        if (mentorData.name.contains(
                                searchQuery.toString(),
                                ignoreCase = true
                            ) || mentorData.position.contains(
                                searchQuery.toString(),
                                ignoreCase = true
                            )
                        ) {
                            Log.d("searchResultsActivity", "Mentor $mentorKey matches search query")
                            if (favoriteMentorIds.contains(mentorKey)) {
                                mentorData.isFavorite = true
                                Log.d("searchResultsActivity", "Mentor $mentorKey is a favorite")
                            }
                            topMentors.add(mentorData)
                            originalTopMentors.add(mentorData)
                        }
                    }
                }
                setupSearchResultsRecyclerView()
            }
        }.addOnFailureListener {
            Log.e("searchResultsActivity", "Error getting data", it)
        }

    }

    private fun setupSearchResultsRecyclerView() {
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView)
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        val verticalLayoutResourceId = R.layout.verticalmentorcards
        searchResultsAdapter = MentorCardAdapter(this, topMentors, verticalLayoutResourceId)
        searchResultsAdapter.setOnItemClickListener { mentor ->
            navigateToMentorAbout(mentor)
        }
        searchResultsRecyclerView.adapter = searchResultsAdapter
    }

    private fun setupFilterSpinner() {
        val items = listOf("Filter", "Available", "Not Available")
        val spinner: Spinner = findViewById(R.id.filterSpinner)
        val adapter = CustomSpinnerAdapter(this, items)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {onNothingSelected(spinner)} // Filter option, do nothing
                    1 -> filterMentors(true) // Available
                    2 -> filterMentors(false) // Not Available
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //if search results adapter has been initialized, update the list with the original list
                if (::searchResultsAdapter.isInitialized) {
                    searchResultsAdapter.updateList(originalTopMentors)
                }

            }
        }
    }

    private fun filterMentors(isAvailable: Boolean) {
        val filteredMentors = if (isAvailable) {
            topMentors.filter { it.availability == "Available" }
        } else if(!isAvailable) {
            topMentors.filter { it.availability == "Not Available" }
        }else{
            originalTopMentors
        }
        searchResultsAdapter.updateList(filteredMentors)
    }

    private fun setupBottomNavigation() {
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

    private fun setupAddMentorButton() {
        val addMentor = findViewById<ImageView>(R.id.addMentorButton)
        addMentor.setOnClickListener {
            val intent = Intent(this, AddAMentor::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToMentorAbout(mentor: Mentor) {
        val intent = Intent(this, aboutMentorPage::class.java)
        Log.d("searchPageActivity", "Navigating to aboutMentorPage with mentor: ${mentor.id}")
        Log.d("searchActivity", "Navigating to aboutMentorPage with mentor: ${mentor.getprofilePictureUrl()}")
        intent.putExtra("mentor", mentor) // Pass the mentor data to the aboutMentorPage
        startActivity(intent)
    }

    private fun setupBackButton() {
        val imageView4 = findViewById<ImageView>(R.id.imageView10)
        imageView4.setOnClickListener {
            onBackPressed()
        }
    }


    private fun setupCardClickListener() {
        if (::searchResultsAdapter.isInitialized) {
            searchResultsAdapter.setOnItemClickListener { position ->
                val intent = Intent(this, aboutMentorPage::class.java)
                startActivity(intent)
            }
        } else {
            Log.e("searchResultsActivity", "searchResultsAdapter is not initialized")
        }
    }


}

class CustomSpinnerAdapter(context: Context, items: List<String>) : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view.findViewById(android.R.id.text1) as TextView).setTextColor(ContextCompat.getColor(context, R.color.dropdownmenu))
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view.findViewById(android.R.id.text1) as TextView).setTextColor(ContextCompat.getColor(context, R.color.dropdownmenu))
        return view
    }
}
