    package com.muhammadfarazrashid.i2106595;

    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.AdapterView
    import android.widget.ArrayAdapter
    import android.widget.Spinner
    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView

    class MainActivity : AppCompatActivity() {

//        private lateinit var topMentorsRecycler: RecyclerView
//        private lateinit var recentMentorsRecycler: RecyclerView
//        private lateinit var educationMentorsRecycler: RecyclerView
//
//        private lateinit var topMentors: ArrayList<Mentor>
//        private lateinit var recentMentors: ArrayList<Mentor>
//        private lateinit var educationMentors: ArrayList<Mentor>
//        private lateinit var badgesRecycler: RecyclerView // Add this line
//        private lateinit var badges: ArrayList<Badge> // Add this line
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContentView(R.layout.home_page)
//
//            // Initialize top mentors
//            topMentorsRecycler = findViewById(R.id.topMentorsRecycler)
//            topMentorsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            topMentors = ArrayList()
//            topMentors.add(Mentor("Faraz Rashid", "Android Developer", "available", "$5000/hr"))
//            topMentors.add(Mentor("John Doe", "UX Designer", "unavailable", "$7000/hr"))
//            topMentors.add(Mentor("Jane Doe", "UI Designer", "available", "$6000/hr"))
//            topMentors.add(Mentor("John Smith", "Web Developer", "unavailable", "$8000/hr"))
//            topMentors.add(Mentor("Jane Smith", "Web Developer", "available", "$9000/hr"))
//            val topMentorsAdapter = MentorCardAdapter(this, topMentors)
//            topMentorsRecycler.adapter = topMentorsAdapter
//
//            // Initialize recent mentors
//            recentMentorsRecycler = findViewById(R.id.recentMentorsRecycler)
//            recentMentorsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            recentMentors = ArrayList()
//            recentMentors.add(Mentor("Mentor 1", "Job 1", "available", "$6000/hr"))
//            recentMentors.add(Mentor("Mentor 2", "Job 2", "unavailable", "$7000/hr"))
//            val recentMentorsAdapter = MentorCardAdapter(this, recentMentors)
//            recentMentorsRecycler.adapter = recentMentorsAdapter
//
//            // Initialize education mentors
//            educationMentorsRecycler = findViewById(R.id.educationMentorsRecycler)
//            educationMentorsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//            educationMentors = ArrayList()
//            educationMentors.add(Mentor("Mentor 1", "Title 3", "available", "$8000/hr"))
//            educationMentors.add(Mentor("2", "Title 4", "unavailable", "$9000/hr"))
//            val educationMentorsAdapter = MentorCardAdapter(this, educationMentors)
//            educationMentorsRecycler.adapter = educationMentorsAdapter
//
//            badges = ArrayList()
//            badges.add(Badge("Category 1", false))
//            badges.add(Badge("Category 2", false))
//            badges.add(Badge("Category 3", true)) // This badge is selected
//            badges.add(Badge("Category 4", false))
//            badges.add(Badge("Category 5", false))
//
//            badgesRecycler = findViewById(R.id.badgesRecycler)
//            badgesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//
//            val badgeAdapter = BadgeAdapter(badges)
//            badgesRecycler.adapter = badgeAdapter
//
//            // Set the badge click listener
//            badgeAdapter.setOnBadgeClickListener(object : BadgeAdapter.OnBadgeClickListener {
//                override fun onBadgeClick(position: Int) {
//                    // Handle badge click
//                    Log.d("MainActivity", "Badge clicked at position: $position")
//                }
//            })
//        }


//        private lateinit var userCountrySpinner: Spinner
//        private lateinit var userCitySpinner: Spinner

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.resetpassword)

//            // Initialize the Spinner using findViewById
//            userCountrySpinner = findViewById(R.id.userCountryTextBos)
//
//            // Set up the Spinner with data and adapter as needed
//            // Example:
//            val countryList = listOf("Select A Country", "Country 2", "Country 3")
//            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            userCountrySpinner.adapter = adapter
//
//            // Set a listener for item selection if needed
//            userCountrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    // Handle the selected item
//                    val selectedCountry = countryList[position]
//                    // Do something with the selected country
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    // Handle the case where nothing is selected
//                }
//            }
//
//            //do the same for city
//
//            userCitySpinner = findViewById(R.id.userCityTextBox)
//            val cityList = listOf("Select A City", "City 2", "City 3")
//            val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityList)
//            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            userCitySpinner.adapter = adapter2
//            userCitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                    // Handle the selected item
//                    val selectedCity = cityList[position]
//                    // Do something with the selected city
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    // Handle the case where nothing is selected
//                }
//            }
//
//        }

        }
    }
