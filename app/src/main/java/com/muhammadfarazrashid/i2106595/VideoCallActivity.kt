package com.muhammadfarazrashid.i2106595

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas


class VideoCallActivity : AppCompatActivity() {

    private lateinit var timeElapsed: TextView
    private lateinit var endCallButton: ImageButton
    private lateinit var turnCameraButton: ImageButton
    private lateinit var turnOffVideoButton: ImageButton
    private lateinit var galleryButton: ImageButton
    private lateinit var otherUserVideo: FrameLayout
    private lateinit var userVideo: FrameLayout
    private lateinit var currentMentor: Mentor
    private var isJoined: Boolean = false
    private lateinit var agoraEngine: RtcEngine
    private val appId: String = "bc8acf945f744259bf2e3abd5226087b"
    private var channelName: String = "mentorCall"
    private val uid: Int = 0
    private val token: String = "007eJxTYJgQut734KmXgYpT8hPiLJ7nLbf9IO/d6fZ5z9KtYf2Mhm4KDEnJFonJaZYmpmnmJiZGppZJaUapxolJKaZGRmYGFuZJJ3Z9TW0IZGQ4emExEyMDBIL4XAy5qXkl+UXOiTk5DAwAZEMjmQ=="
    private val localSurfaceView: SurfaceView? = null
    private val remoteSurfaceView: SurfaceView? = null
    private lateinit var timer: CountDownTimer
    private var elapsedTimeInSeconds: Long = 0

    private val iRtcEngineEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            showMessage("Remote user joined $uid")
            runOnUiThread { setUpRemoteVideo(uid) }
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            super.onJoinChannelSuccess(channel, uid, elapsed)
            isJoined = true
            showMessage("Joined Channel $channel")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            showMessage("Remote user offline $uid $reason")
            runOnUiThread {
                if (remoteSurfaceView != null) {
                    remoteSurfaceView.visibility = View.GONE
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)
        initViews()
        currentMentor = intent.getParcelableExtra("mentor")!!
        setupTimer()


        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID)
        }

        setupSDKEngine()
        joinChannel(findViewById(android.R.id.content))
        setButtonOnClickListeners()
    }

    private fun setupTimer() {
        timeElapsed = findViewById(R.id.timeElapsedTextView)

        timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                elapsedTimeInSeconds++
                val hours = elapsedTimeInSeconds / 3600
                val minutes = (elapsedTimeInSeconds % 3600) / 60
                val seconds = elapsedTimeInSeconds % 60
                val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                timeElapsed.text = timeString
            }

            override fun onFinish() {
                // Timer finished (Not needed in this case as the timer runs indefinitely)
            }
        }

        // Start the timer
        timer.start()
    }

    // Kotlin


    override fun onDestroy() {
        super.onDestroy()
        agoraEngine.stopPreview()
        agoraEngine.leaveChannel()

    }

    private fun setupSDKEngine():Boolean {
        try {
            val config = RtcEngineConfig()
            config.mContext = this
            config.mAppId = appId
            config.mEventHandler = iRtcEngineEventHandler
            agoraEngine = RtcEngine.create(config)
            agoraEngine.enableVideo()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    // Kotlin
    private fun setUpRemoteVideo(uid: Int) {
        otherUserVideo = findViewById(R.id.photoImageView)
        val remoteSurfaceView = RtcEngine.CreateRendererView(this)
        remoteSurfaceView.setZOrderMediaOverlay(true)
        otherUserVideo.addView(remoteSurfaceView)
        agoraEngine.setupRemoteVideo(VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
    }


    private fun setUpLocalVideo() {
        val localSurfaceView = RtcEngine.CreateRendererView(this)
        localSurfaceView.setZOrderMediaOverlay(true)
        userVideo.addView(localSurfaceView)
        val videoCanvas = VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_FIT, 0)
        agoraEngine.setupLocalVideo(videoCanvas)
        agoraEngine.startPreview()
    }


    open fun joinChannel(view: View) {
        // Ensure that necessary Android permissions have been granted
        if (checkSelfPermission()) {


            val options = ChannelMediaOptions()
            agoraEngine!!.enableVideo()


            // For a Video/Voice call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            // Set the client role to broadcaster or audience
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER

            setUpLocalVideo()
            // Start local preview.
            agoraEngine!!.startPreview()

            // Join the channel using a token.
            agoraEngine!!.joinChannel(token, channelName, uid, options)
        }
        else{
            showMessage("Permissions were not granted")
        }
    }



    fun leaveChannel() {
        if (!isJoined) {
            showMessage("Join a channel first")
        } else {
            agoraEngine!!.leaveChannel()
            showMessage("You left the channel")
            if (otherUserVideo != null) otherUserVideo!!.visibility = View.GONE
            if (userVideo != null) userVideo!!.visibility = View.GONE
            isJoined = false
        }
    }

    private fun initViews() {
        timeElapsed = findViewById(R.id.timeElapsedTextView)
        endCallButton = findViewById(R.id.bottomVideoButton)
        turnCameraButton = findViewById(R.id.turnCamera)
        turnOffVideoButton = findViewById(R.id.turnOffVideoButton)
        galleryButton = findViewById(R.id.galleryButton)
        userVideo = findViewById(R.id.userVideo) // Initialize userVideo FrameLayout



    }

    private fun setButtonOnClickListeners() {
        endCallButton.setOnClickListener { leaveChannel()
            onBackPressed() }
        turnCameraButton.setOnClickListener {

            if (::agoraEngine.isInitialized) {
                agoraEngine.switchCamera()

            } else {
               showMessage("RtcEngine is not initialized")
            }
            turnOffVideoButton.setOnClickListener { leaveChannel() }
            galleryButton.setOnClickListener { /* Open gallery logic */ }
        }
         var isVideoEnabled = true // Track if video is currently enabled

        turnOffVideoButton.setOnClickListener {
            if (::agoraEngine.isInitialized) {
                if (isVideoEnabled) {
                    // Disable video
                    agoraEngine.muteLocalVideoStream(true)

                    showMessage("Video turned off")
                } else {
                    // Enable video
                    agoraEngine.muteLocalVideoStream(false)
                    //parse green color

                    showMessage("Video turned on")
                }
                // Toggle the state of video enabled
                isVideoEnabled = !isVideoEnabled
            } else {
                showMessage("RtcEngine is not initialized")
            }
        }

    }
    private fun checkSelfPermission(): Boolean {
        for (permission in REQUESTED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PERMISSION_REQ_ID = 22
        private val REQUESTED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}
