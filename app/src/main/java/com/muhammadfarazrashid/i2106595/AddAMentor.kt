package com.muhammadfarazrashid.i2106595

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddAMentor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addmentorpage)

        //cllick on imageview11 and go back

        val imageView11 = findViewById<ImageView>(R.id.imageView11)
        imageView11.setOnClickListener {
            onBackPressed()
        }

        //click on signupbutton2 and go back to homepage

        val signupbutton2 = findViewById<Button>(R.id.signupButton2)
        signupbutton2.setOnClickListener {
            onBackPressed()
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menu_search -> {
                    val intent = Intent(this, searchPageActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_home -> {
                    val intent = Intent(this, homePageActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_chat -> {
                    val intent = Intent(this, mainChatActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_profile -> {
                    val intent = Intent(this, MyProfileActivity::class.java)
                    startActivity(intent)
                }

            }

        }
    }
}