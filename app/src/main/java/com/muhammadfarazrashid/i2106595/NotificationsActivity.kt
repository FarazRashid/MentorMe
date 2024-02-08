package com.muhammadfarazrashid.i2106595

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationsActivity : AppCompatActivity() {

    private lateinit var notifcationsRecycler: RecyclerView
    private lateinit var notificationsAdapter: RecentSearchesAdapter
    private val notificationsList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        notifcationsRecycler = findViewById(R.id.notificationsRecyclerView)

        // Create a list of recent searches (replace with your actual data)
        notificationsList.addAll(getSampleRecentSearches())

        // Initialize the adapter with the list and set the click listener
        val onRemoveClickListener =
            RecentSearchesAdapter.OnRemoveClickListener { position ->
                notificationsAdapter.removeRecentSearch(
                    position
                )
            }

        notificationsAdapter = RecentSearchesAdapter(notificationsList, onRemoveClickListener, "notifications")

        notifcationsRecycler.layoutManager = LinearLayoutManager(this)
        notifcationsRecycler.adapter = notificationsAdapter

        val clearAllButton: TextView= findViewById(R.id.filterSpinner)
        clearAllButton.setOnClickListener {
            clearAllNotifications()
        }


    }

    private fun getSampleRecentSearches(): List<String> {
        val recentSearches = mutableListOf<String>()
        recentSearches.add("Mentor 1")
        recentSearches.add("Mentor 2")
        recentSearches.add("Mentor 3")
        // Add more items as needed
        return recentSearches
    }

    private fun clearAllNotifications() {
        notificationsList.clear()
        notificationsAdapter.notifyDataSetChanged()
    }
}