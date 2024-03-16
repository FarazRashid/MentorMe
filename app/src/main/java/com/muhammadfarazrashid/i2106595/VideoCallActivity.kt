package com.muhammadfarazrashid.i2106595

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView

class VideoCallActivity : AppCompatActivity() {

    private lateinit var timeElapsed: TextView
    private lateinit var endCallButton: ImageButton
    private lateinit var turnCameraButton: ImageButton
    private lateinit var turnOffVideoButton: ImageButton
    private lateinit var galleryButton: ImageButton
    private lateinit var otherUserVideo: VideoView
    private lateinit var userVideo: VideoView
    private lateinit var currentMentor: Mentor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)
        initViews()

        currentMentor = intent.getParcelableExtra<Mentor>("mentor")!!



        setButtonOnClickListeners()

    }


    private fun initViews()
    {
        timeElapsed = findViewById(R.id.timeElapsedTextView)
        endCallButton = findViewById(R.id.bottomVideoButton)
        turnCameraButton = findViewById(R.id.turnCamera)
        turnOffVideoButton = findViewById(R.id.turnOffVideoButton)
        galleryButton = findViewById(R.id.galleryButton)
        otherUserVideo = findViewById(R.id.photoImageView)
        userVideo = findViewById(R.id.userVideo)
    }

    private fun setButtonOnClickListeners()
    {

        endCallButton.setOnClickListener {
            onBackPressed()
        }


        turnCameraButton.setOnClickListener {
            //turn camera
        }

        turnOffVideoButton.setOnClickListener {
            //turn off video
        }

        galleryButton.setOnClickListener {
            //open gallery
        }



    }

}