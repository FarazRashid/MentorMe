package com.muhammadfarazrashid.i2106595

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfarazrashid.i2106595.RecentSearchesAdapter.OnRemoveClickListener

class searchPageActivity : AppCompatActivity() {

    private lateinit var recentSearchesRecycler: RecyclerView
    private lateinit var recentSearchesAdapter: RecentSearchesAdapter
    private val recentSearchesList = mutableListOf<String>()
    private lateinit var categoriesRecycler: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchpage)

        // Initialize the RecyclerView
        recentSearchesRecycler = findViewById(R.id.recentSearchesRecycler)

        // Create a list of recent searches (replace with your actual data)
        recentSearchesList.addAll(getSampleRecentSearches())

        // Initialize the adapter with the list and set the click listener
        val onRemoveClickListener =
            OnRemoveClickListener { position -> recentSearchesAdapter.removeRecentSearch(position) }

        recentSearchesAdapter = RecentSearchesAdapter(recentSearchesList, onRemoveClickListener)


        // Set up the RecyclerView with the adapter
        recentSearchesRecycler.layoutManager = LinearLayoutManager(this)
        recentSearchesRecycler.adapter = recentSearchesAdapter

        categoriesRecycler = findViewById(R.id.categoriesRecycler)

        // Create a list of categories (replace with your actual data)
        val categoriesList = getSampleCategories()

        // Initialize the adapter with the list and set the click listener
        val onCategoryClickListener = object : CategoriesAdapter.OnCategoryClickListener {
            override fun onCategoryClick(position: Int) {
                // Handle category click event, e.g., open a new activity
            }
        }

        categoriesAdapter = CategoriesAdapter(categoriesList, onCategoryClickListener)

        // Set up the RecyclerView with the adapter
        categoriesRecycler.layoutManager = LinearLayoutManager(this)
        categoriesRecycler.adapter = categoriesAdapter
    }


    // Replace this with your actual data source
    private fun getSampleRecentSearches(): List<String> {
        val recentSearches = mutableListOf<String>()
        recentSearches.add("Mentor 1")
        recentSearches.add("Mentor 2")
        recentSearches.add("Mentor 3")
        // Add more items as needed
        return recentSearches
    }

        // Replace this with your actual data source
        private fun getSampleCategories(): List<Category> {
            val categories = mutableListOf<Category>()
            categories.add(Category("Entrepreneurship", R.drawable.entrepeneur))
            categories.add(Category("Self Improvement", R.drawable.selfimprovementicon))
            categories.add(Category("Education", R.drawable.educationicon))
            // Add more items as needed
            return categories
        }



}

