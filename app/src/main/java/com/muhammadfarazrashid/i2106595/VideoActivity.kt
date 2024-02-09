package com.muhammadfarazrashid.i2106595

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        //click on cross button and go back

        val crossButton = findViewById<Button>(R.id.crossButton)
        crossButton.setOnClickListener {
            onBackPressed()
        }

        val constraintLayout: ConstraintLayout = findViewById(R.id.bottomLayout)
        val videoTextView: TextView = constraintLayout.findViewById(R.id.photoButton)

        // Set click listener on the Video TextView
        videoTextView.setOnClickListener {
            // Start VideoActivity when the TextView is clicked
            val intent = Intent(this@VideoActivity, PhotoActivity::class.java)
            startActivity(intent)
        }
    }
}