package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class loginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val signupText: TextView = findViewById(R.id.textView11)

        // Set OnClickListener for the signup text
        signupText.setOnClickListener {
            val intent = Intent(this, signUpActivity::class.java)
            startActivity(intent)
        }

        val forgotPasswordText: TextView = findViewById(R.id.forgotYourPasswordText)

        // Set OnClickListener for the forgot password text
        forgotPasswordText.setOnClickListener {
            val intent = Intent(this, forgotPasswordActivity::class.java)
            startActivity(intent)
        }

        val loginButton: Button = findViewById(R.id.signupButton)

        loginButton.setOnClickListener {
            val intent = Intent(this, homePageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}