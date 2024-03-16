package com.muhammadfarazrashid.i2106595

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig

class PhoneCallActivity : AppCompatActivity() {

    private lateinit var userName: TextView
    private lateinit var timeElapsed: TextView
    private lateinit var muteButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var speakerButton: ImageButton
    private lateinit var endCallButton: ImageButton
    private lateinit var currentMentor: Mentor
    private lateinit var agoraEngine: RtcEngine
    private val appId: String = "bc8acf945f744259bf2e3abd5226087b"
    private var channelName: String = "mentorCall"
    private val uid: Int = 0
    private val token: String = "007eJxTYJgQut734KmXgYpT8hPiLJ7nLbf9IO/d6fZ5z9KtYf2Mhm4KDEnJFonJaZYmpmnmJiZGppZJaUapxolJKaZGRmYGFuZJJ3Z9TW0IZGQ4emExEyMDBIL4XAy5qXkl+UXOiTk5DAwAZEMjmQ=="
    private lateinit var timer: CountDownTimer
    private var elapsedTimeInSeconds: Long = 0
    private var mentorPhoto: ImageView? = null

    private val iRtcEngineEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            showMessage("Remote user joined $uid")
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            super.onJoinChannelSuccess(channel, uid, elapsed)
            showMessage("Joined Channel $channel")

        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            showMessage("Remote user offline $uid $reason")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_call)
        initViews()

        currentMentor = intent.getParcelableExtra<Mentor>("mentor")!!
        setMentorDetails(currentMentor)
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

    override fun onDestroy() {
        super.onDestroy()
        agoraEngine.leaveChannel()
    }

    private fun setupSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = this
            config.mAppId = appId
            config.mEventHandler = iRtcEngineEventHandler
            agoraEngine = RtcEngine.create(config)
            agoraEngine.enableAudio()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun joinChannel(view: View) {
        // Ensure that necessary Android permissions have been granted
        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()
            agoraEngine!!.enableAudio()

            // For an audio call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER

            // Join the channel using a token.
            agoraEngine!!.joinChannel(token, channelName, uid, options)
        } else {
            showMessage("Permissions were not granted")
        }
    }

    fun leaveChannel() {
        agoraEngine!!.leaveChannel()
        showMessage("You left the channel")
    }

    private fun initViews() {
        userName = findViewById(R.id.userName)
        timeElapsed = findViewById(R.id.timeElapsedTextView)
        muteButton = findViewById(R.id.muteButton)
        pauseButton = findViewById(R.id.pauseButton)
        speakerButton = findViewById(R.id.turnOffVideoButton)
        endCallButton = findViewById(R.id.bottomVideoButton)
        mentorPhoto = findViewById(R.id.imageView9)
    }

    private fun setMentorDetails(mentor: Mentor) {
        // Set mentor's name
        userName.text = currentMentor.getName()
        Picasso.get().load(currentMentor.getprofilePictureUrl()).into(mentorPhoto)
    }

    private fun setButtonOnClickListeners() {
        endCallButton.setOnClickListener {
            leaveChannel()
            onBackPressed()
        }
        var isMuted = false

        muteButton.setOnClickListener {
            // Mute the call

            if (::agoraEngine.isInitialized) {
                if (isMuted) {
                    // Disable video
                    agoraEngine.muteLocalAudioStream(true)

                    showMessage("Audio turned off")
                } else {
                    // Enable video
                    agoraEngine.muteLocalAudioStream(false)
                    //parse green color

                    showMessage("Audio turned on")
                }
                // Toggle the state of video enabled
                isMuted = !isMuted
            } else {
                showMessage("RtcEngine is not initialized")
            }
        }

        pauseButton.setOnClickListener {
            // Pause the call
        }
        var isSpeaker:Boolean= false
        speakerButton.setOnClickListener {
            //switch between speaker and earpiece

            if (::agoraEngine.isInitialized) {
                if (isSpeaker) {
                    // Disable video
                    agoraEngine.setEnableSpeakerphone(false)
                    showMessage("Speaker turned off")
                } else {
                    // Enable video
                    agoraEngine.setEnableSpeakerphone(true)
                    //parse green color
                    speakerButton.setBackgroundColor(resources.getColor(R.color.red))
                    showMessage("Speaker turned on")
                }
                // Toggle the state of video enabled
                isSpeaker = !isSpeaker
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

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PERMISSION_REQ_ID = 22
        private val REQUESTED_PERMISSIONS = arrayOf(
            Manifest.permission.RECORD_AUDIO
        )
    }
}
