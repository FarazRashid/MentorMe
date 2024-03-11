package com.muhammadfarazrashid.i2106595.dataclasses

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var country: String = "",
    var city: String = "",
    var password: String = "",
    var phone: String = "",
    var profilePictureUrl: String = "",
    var bannerImageUrl: String = "",

) {
    // No-argument constructor required for Firebase deserialization
    constructor() : this("", "", "", "", "", "", "", "", "")
}
fun getUserWithEmail(email: String, completion: (User?) -> Unit) {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

    // Check if the current user's email matches the provided email
    if (currentUserEmail != null && currentUserEmail == email) {
        // Retrieve the user from the database based on the provided email
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Iterate through the dataSnapshot to find the user with the provided email
                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null && user.email == email) {
                        // User found, invoke the completion callback with the user object
                        completion(user)
                        return
                    }
                }
                // No user found with the provided email, invoke the completion callback with null
                completion(null)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Error occurred while fetching user data, invoke the completion callback with null
                completion(null)
            }
        })
    } else {
        // If the current user's email doesn't match the provided email, invoke the completion callback with null
        completion(null)
    }
}

