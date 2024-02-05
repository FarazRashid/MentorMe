package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class aboutMentorPage: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aboutmentorpage)

        //click on image view 4 go back to searchresults page

        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        imageView4.setOnClickListener {
            val intent = Intent(this, homePageActivity::class.java)
            startActivity(intent)
        }

    }
}