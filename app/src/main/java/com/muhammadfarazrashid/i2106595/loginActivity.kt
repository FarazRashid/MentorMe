package com.muhammadfarazrashid.i2106595

import UserManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class loginActivity : AppCompatActivity() {

    private  var mAuth : FirebaseAuth = FirebaseAuth.getInstance()

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
            login()
        }
    }

    fun verifyTextFields() : Boolean
    {
        if(findViewById<TextView>(R.id.userEmail).text.toString().isEmpty())
        {
            findViewById<TextView>(R.id.userEmail).error = "Please enter your email"
            return false
        }
        if(findViewById<TextView>(R.id.userPassword).text.toString().isEmpty())
        {
            findViewById<TextView>(R.id.userPassword).error = "Please enter your password"
            return false
        }

        return true
    }

    fun login() {
        if(!verifyTextFields())
        {
            return
        }
        val email: String = findViewById<TextView>(R.id.userEmail).text.toString()
        val password: String = findViewById<TextView>(R.id.userPassword).text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val intent = Intent(this, homePageActivity::class.java)
                    //call user managerclass and save it
                    val userManager = UserManager.getInstance()

                    userManager.fetchAndSetCurrentUser(user?.email.toString())
                    {
                        //check if usermanager is properly initialising

                        startActivity(intent)
                        finish()
                    }
                } else {

                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT).show()

                }
            }
    }


}