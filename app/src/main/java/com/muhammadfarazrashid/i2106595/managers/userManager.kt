
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.muhammadfarazrashid.i2106595.dataclasses.User
import com.muhammadfarazrashid.i2106595.dataclasses.getUserWithEmail
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

object UserManager {


    // Singleton instance
    private var instance: UserManager? = null

    fun getInstance(): UserManager {
        if (instance == null) {
            instance = UserManager
        }
        return instance!!
    }

    private lateinit var currentUser: User

    fun getCurrentUser(): User? {
        return if (::currentUser.isInitialized) currentUser else null
    }


    fun setCurrentUser(user: User) {
        currentUser = user
    }

    fun setUserUrl(url: String) {
        this.currentUser.profilePictureUrl = url
    }

    fun getUserUrl(): String {
        return this.currentUser.profilePictureUrl
    }

    fun fetchAndSetCurrentUser(email: String, callback: () -> Unit) {
        getUserWithEmail(email) { user ->
            if (user != null) {
                currentUser = user
                setCurrentUser(user)
                Log.d(TAG, "loadUserInformation: ${currentUser.id}")
                callback.invoke() // Execute the callback function
            }
        }


    }
}
