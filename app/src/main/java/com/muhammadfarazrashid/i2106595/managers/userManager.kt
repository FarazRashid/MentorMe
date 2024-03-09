package com.muhammadfarazrashid.i2106595.managers

import com.google.firebase.auth.FirebaseAuth
import com.muhammadfarazrashid.i2106595.dataclasses.User
import com.muhammadfarazrashid.i2106595.dataclasses.getUserWithEmail

object UserManager {
    private var currentUser: User? = null

    fun getCurrentUser(): User? {
        return currentUser
    }

    fun setCurrentUser(user: User?) {
        currentUser = user
    }

    fun fetchAndSetCurrentUser() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        if (userEmail != null) {
            getUserWithEmail(userEmail) { user ->
                currentUser = user
            }
        }
    }
}
