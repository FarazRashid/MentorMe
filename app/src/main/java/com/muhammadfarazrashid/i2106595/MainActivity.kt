package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.muhammadfarazrashid.i2106595.dataclasses.NotificationsManager
import android.app.Application
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entrancepage)


        NotificationsManager.createNotificationChannel(this)

        //if user logged in, go to home page
        if (UserManager.getInstance().getUserLoggedInSP(getSharedPreferences("USER_LOGIN", MODE_PRIVATE))) {

            UserManager.getInstance().getUserEmailSP(getSharedPreferences("USER_LOGIN", MODE_PRIVATE))?.let {

                val intent = Intent(this, homePageActivity::class.java)

                UserManager.getInstance().fetchAndSetCurrentUser(it)
                {
                    //add logged in boolean to shared preferences
                    startActivity(intent)
                    finish()
                }

            }
        } else {
            // Delay for 5000 milliseconds (5 seconds)
            Handler().postDelayed({
                val intent = Intent(this, loginActivity::class.java)
                startActivity(intent)
                finish()
            }, 5000)
        }
    }
}
