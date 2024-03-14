package com.muhammadfarazrashid.i2106595

import android.media.MediaRecorder
import java.io.IOException


class AudioRecorder {
    private var mediaRecorder: MediaRecorder? = null
    private fun initMediaRecorder() {
        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
    }

    @Throws(IOException::class)
    fun start(filePath: String?) {
        if (mediaRecorder == null) {
            initMediaRecorder()
        }
        mediaRecorder!!.setOutputFile(filePath)
        mediaRecorder!!.prepare()
        mediaRecorder!!.start()
    }

    fun stop() {
        try {
            mediaRecorder!!.stop()
            destroyMediaRecorder()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun destroyMediaRecorder() {
        mediaRecorder!!.release()
        mediaRecorder = null
    }
}