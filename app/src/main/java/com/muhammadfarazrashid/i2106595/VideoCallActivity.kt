package com.muhammadfarazrashid.i2106595

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class VideoCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)

        //click on bottomnavigationview and go back

        val bottomNavigationView = findViewById<ImageView>(R.id.bottomVideoButton)

        bottomNavigationView.setOnClickListener {
            onBackPressed()
        }

    }
}