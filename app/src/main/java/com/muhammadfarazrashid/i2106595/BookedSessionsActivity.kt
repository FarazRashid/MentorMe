package com.muhammadfarazrashid.i2106595

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
    }
}