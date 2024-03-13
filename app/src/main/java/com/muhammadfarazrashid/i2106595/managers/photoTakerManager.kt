package com.muhammadfarazrashid.i2106595.managers

object photoTakerManager {
    private var instance: photoTakerManager? = null

    private var isTakingPhoto: Boolean = false

    private var imageUrl = ""

    private var photoStatus:Int = 0

    fun getInstance(): photoTakerManager {
        if (instance == null) {
            instance = photoTakerManager
        }
        return instance!!
    }

    fun setIsTakingPhoto(isTakingPhoto: Boolean) {
        this.isTakingPhoto = isTakingPhoto
    }

    fun getIsTakingPhoto(): Boolean {
        return this.isTakingPhoto
    }

    fun setImageUrl(url: String) {
        this.imageUrl = url
    }

    fun getImageUrl(): String {
        return this.imageUrl
    }

    fun setPhotoStatus(status: Int) {
        this.photoStatus = status
    }

    fun getPhotoStatus(): Int {
        return this.photoStatus
    }


}