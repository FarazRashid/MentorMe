package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entrancepage)

        // Delay for 2000 milliseconds (2 seconds)
        Handler().postDelayed({
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}
