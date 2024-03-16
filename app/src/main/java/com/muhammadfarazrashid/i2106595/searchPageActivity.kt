package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        initializeRecentSearches()
        initializeCategories()
        setupSearchView()
        setupBottomNavigation()
        setupAddMentorButton()
        setupBackButton()
    }

    private fun initializeRecentSearches() {
        recentSearchesRecycler = findViewById(R.id.recentSearchesRecycler)
        recentSearchesList.addAll(getSampleRecentSearches())
        val onRemoveClickListener =
            OnRemoveClickListener { position -> recentSearchesAdapter.removeRecentSearch(position) }
        recentSearchesAdapter = RecentSearchesAdapter(recentSearchesList, onRemoveClickListener, "recentSearches")
        recentSearchesRecycler.layoutManager = LinearLayoutManager(this)
        recentSearchesRecycler.adapter = recentSearchesAdapter
    }

    private fun initializeCategories() {
        categoriesRecycler = findViewById(R.id.categoriesRecycler)
        val categoriesList = getSampleCategories()
        val onCategoryClickListener = object : CategoriesAdapter.OnCategoryClickListener {
            override fun onCategoryClick(position: Int) {
                // Handle category click event, e.g., open a new activity
            }
        }
        categoriesAdapter = CategoriesAdapter(categoriesList, onCategoryClickListener)
        categoriesRecycler.layoutManager = LinearLayoutManager(this)
        categoriesRecycler.adapter = categoriesAdapter
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    val intent = Intent(this@searchPageActivity, searchResultsActivity::class.java)
                    intent.putExtra("search_query", query)
                    startActivity(intent)
                    return true
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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

    private fun setupBackButton() {
        val imageView4 = findViewById<ImageView>(R.id.imageView8)
        imageView4.setOnClickListener {
            onBackPressed()
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

    private fun getSampleCategories(): List<Category> {
        val categories = mutableListOf<Category>()
        categories.add(Category("Entrepreneurship", R.drawable.entrepeneur))
        categories.add(Category("Self Improvement", R.drawable.selfimprovementicon))
        categories.add(Category("Education", R.drawable.educationicon))
        // Add more items as needed
        return categories
    }
}
