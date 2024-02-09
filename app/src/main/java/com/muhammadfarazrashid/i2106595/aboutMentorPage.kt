package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        //onclick review button go to review page

        val reviewButton = findViewById<Button>(R.id.reviewButton)
        reviewButton.setOnClickListener {
            val intent = Intent(this, reviewpage::class.java)
            startActivity(intent)
        }

        //click on join community and go to community chat

        val joinCommunity = findViewById<Button>(R.id.communityButton)
        joinCommunity.setOnClickListener {
            val intent = Intent(this, communityChatActivity::class.java)
            startActivity(intent)
        }

        //click on signup button and go to calender page

        val signUpButton = findViewById<Button>(R.id.signupButton)
        signUpButton.setOnClickListener {
            val intent = Intent(this, calenderPage::class.java)
            startActivity(intent)
        }

    }
}