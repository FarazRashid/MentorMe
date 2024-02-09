package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class reviewpage: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewpage)


        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        imageView4.setOnClickListener {
            val intent = Intent(this, homePageActivity::class.java)
            startActivity(intent)
        }

        //click on signup button and go back to home page

        val imageView5 = findViewById<Button>(R.id.signupButton)

        imageView5.setOnClickListener {
            val intent = Intent(this, homePageActivity::class.java)
            startActivity(intent)
        }
    }


}