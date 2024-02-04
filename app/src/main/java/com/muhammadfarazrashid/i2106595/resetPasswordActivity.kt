package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class resetPasswordActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resetpassword)

        //click on image view 4 and go back to login

        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        imageView4.setOnClickListener {
            val intent = Intent(this, forgotPasswordActivity::class.java)
            startActivity(intent)
        }

        //click on login text and go back to login

        val loginText = findViewById<TextView>(R.id.textView11)
        loginText.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }

        //click on send button and go to reset password

        val sendButton = findViewById<TextView>(R.id.signupButton)
        sendButton.setOnClickListener {
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
    }
}
