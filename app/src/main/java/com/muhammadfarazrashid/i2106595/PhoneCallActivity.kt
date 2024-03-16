package com.muhammadfarazrashid.i2106595

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PhoneCallActivity : AppCompatActivity() {

    private lateinit var userName: TextView
    private lateinit var timeElapsed: TextView
    private lateinit var userImage: CircleImageView
    private lateinit var muteButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var speakerButton: ImageButton
    private lateinit var endCallButton: ImageButton
    private lateinit var currentMentor: Mentor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_call)
        initViews()

        currentMentor = intent.getParcelableExtra<Mentor>("mentor")!!
        setMentorDetails(currentMentor)


        setButtonOnClickListeners()

    }

    private fun setMentorDetails(mentor: Mentor)
    {
        //set mentor's name and image
        Picasso.get().load(currentMentor.getprofilePictureUrl()).into(userImage)
        userName.text = currentMentor.getName()
    }


    private fun initViews()
    {
        userName = findViewById(R.id.userName)
        timeElapsed = findViewById(R.id.timeElapsedTextView)
        userImage = findViewById(R.id.imageView9)
        muteButton = findViewById(R.id.muteButton)
        pauseButton = findViewById(R.id.pauseButton)
        speakerButton = findViewById(R.id.turnOffVideoButton)
        endCallButton = findViewById(R.id.bottomVideoButton)
    }

    private fun setButtonOnClickListeners()
    {

        endCallButton.setOnClickListener {
            onBackPressed()
        }

        //initalize other buttons and set up dummy functions which we'll implement later

        muteButton.setOnClickListener {
            //mute the call
        }

        pauseButton.setOnClickListener {
            //pause the call
        }

        speakerButton.setOnClickListener {
            //turn off speaker
        }


    }

}