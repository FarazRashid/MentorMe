package com.muhammadfarazrashid.i2106595

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class searchResultsActivity : AppCompatActivity() {

    private lateinit var searchResultsRecyclerView: RecyclerView
    private lateinit var searchResultsAdapter: MentorCardAdapter
    private lateinit var topMentors: ArrayList<Mentor>
    private lateinit var badgesRecycler: RecyclerView
    private lateinit var badges: ArrayList<Badge>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchresults)

        // Initialize top mentors
        topMentors = ArrayList()
        topMentors.add(Mentor("Faraz Rashid", "Android Developer", "available", "$5000/hr"))
        topMentors.add(Mentor("John Doe", "UX Designer", "unavailable", "$7000/hr"))
        topMentors.add(Mentor("Jane Doe", "UI Designer", "available", "$6000/hr"))
        topMentors.add(Mentor("John Smith", "Web Developer", "unavailable", "$8000/hr"))
        topMentors.add(Mentor("Jane Smith", "Web Developer", "available", "$9000/hr"))

        // Set up top mentors RecyclerView
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView)
        searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Choose the layout resource ID based on the card type (horizontal or vertical)
        val horizontalLayoutResourceId = R.layout.mentorcard
        val verticalLayoutResourceId = R.layout.verticalmentorcards

        // Initialize the adapter with the chosen layout resource ID
        searchResultsAdapter = MentorCardAdapter(this, topMentors, verticalLayoutResourceId)
        searchResultsRecyclerView.adapter = searchResultsAdapter

        // Initialize your Spinner and ArrayAdapter in your activity

// Add items to the ArrayAdapter
        val items = listOf("Filter", "Item 2", "Item 3")

/// Initialize your Spinner and ArrayAdapter in your activity
        val spinner: Spinner = findViewById(R.id.filterSpinner)
        val adapter = CustomSpinnerAdapter(this, items)

// Set the ArrayAdapter on the Spinner
        spinner.adapter = adapter



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

