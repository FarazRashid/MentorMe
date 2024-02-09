package com.muhammadfarazrashid.i2106595

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EditProfilePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile_page)

        //click on back button and go back

        val backButton = findViewById<Button>(R.id.backbutton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        //click on submit button and go back to profile page

        val submitButton = findViewById<Button>(R.id.bookAnAppointmentButton)
        submitButton.setOnClickListener {
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }
    }
}