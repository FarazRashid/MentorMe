package com.muhammadfarazrashid.i2106595

import UserManager
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.muhammadfarazrashid.i2106595.dataclasses.User
import com.muhammadfarazrashid.i2106595.dataclasses.getUserWithEmail
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import java.io.IOException


class MyProfileActivity : AppCompatActivity() {

    private lateinit var reviewAdapter: ReviewItemAdapter
    private lateinit var topMentorsRecycler: RecyclerView
    private lateinit var topMentors: ArrayList<Mentor>
    private lateinit var currentUser: User
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var name: TextView
    private lateinit var city: TextView
    private lateinit var banner: ImageView
    private lateinit var profilePicture: ImageView
    private lateinit var editProfilePicture: ImageView
    private lateinit var editProfileBanner: ImageView
    private var selectedImageRequestCode: Int = 0
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        name = findViewById(R.id.nameText)
        city = findViewById(R.id.location)
        banner = findViewById(R.id.bannerImage)
        profilePicture = findViewById(R.id.userProfilePhoto)
        editProfilePicture = findViewById(R.id.editProfilePicture)
        editProfileBanner = findViewById(R.id.editBanner)
        loadUserInformation()

        // Retrieve the RecyclerView using its ID
        val myReviewsRecycler = findViewById<RecyclerView>(R.id.myReviewsRecycler)
        myReviewsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Sample data for reviews
        val reviewList = getSampleReviewData()

        // Initialize the adapter
        reviewAdapter = ReviewItemAdapter(reviewList)
        myReviewsRecycler.adapter = reviewAdapter

        topMentorsRecycler = findViewById(R.id.topMentorsRecycler)
        topMentorsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topMentors = ArrayList()
        topMentors.add(Mentor("Faraz Rashid", "Android Developer", "available", "$5000/hr"))
        topMentors.add(Mentor("John Doe", "UX Designer", "unavailable", "$7000/hr"))
        topMentors.add(Mentor("Jane Doe", "UI Designer", "available", "$6000/hr"))
        topMentors.add(Mentor("John Smith", "Web Developer", "unavailable", "$8000/hr"))
        topMentors.add(Mentor("Jane Smith", "Web Developer", "available", "$9000/hr"))

        val horizontalLayoutResourceId = R.layout.mentorcard
        val topMentorsAdapter = MentorCardAdapter(this, topMentors, horizontalLayoutResourceId)
        topMentorsRecycler.adapter = topMentorsAdapter


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menu_search -> {
                    val intent = Intent(this, searchPageActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_home -> {
                    val intent = Intent(this, homePageActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_chat -> {
                    val intent = Intent(this, mainChatActivity::class.java)
                    startActivity(intent)
                }
                R.id.menu_profile -> {
                    val intent = Intent(this, MyProfileActivity::class.java)
                    startActivity(intent)
                }

            }

        }

        //click on add mentor button and go to add mentor page
        val addMentor = findViewById<ImageView>(R.id.addMentorButton)
        addMentor.setOnClickListener {
            val intent = Intent(this, AddAMentor::class.java)
            startActivity(intent)
        }

        //click on bookedsessions and go to bookedsessions page

        val bookedSessions = findViewById<View>(R.id.bookedSessions)
        bookedSessions.setOnClickListener {
            val intent = Intent(this, BookedSessionsActivity::class.java)
            startActivity(intent)
        }

        //click on edit profile image and go to editprofile page

        val editProfile = findViewById<View>(R.id.editProfile)

        editProfile.setOnClickListener {
            val intent = Intent(this, EditProfilePageActivity::class.java)
            startActivity(intent)
        }

        //click on back button and go back

        val backButton = findViewById<Button>(R.id.backbutton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        profilePicture.setOnClickListener {
            editProfilePicture()
        }

        banner.setOnClickListener {
            editProfileBanner()
        }

    }

    //when user comes back from editprofile page, reload the user information
    override fun onResume() {
        super.onResume()
        UserManager.fetchAndSetCurrentUser(mAuth.currentUser?.email.toString())
        {
            loadUserInformation()
        }
    }

    private fun getSampleReviewData(): List<ReviewItem> {
        val reviewList = ArrayList<ReviewItem>()

        // Add sample reviews
        reviewList.add(ReviewItem("John Cooper", "John provided excellent prototyping techniques and insights. I highly recommend him!", 5))
        reviewList.add(ReviewItem("Alice Smith", "Great mentor, very knowledgeable in app development.", 4))
        reviewList.add(ReviewItem("Emma Johnson", "Average mentor, could improve communication skills.", 3))

        return reviewList
    }


    private fun loadUserInformation() {
        val currentUser = UserManager.getCurrentUser()
        val userEmail = mAuth.currentUser?.email
        if (currentUser != null) {

            Log.d(ContentValues.TAG, "loadUserInformation: ${currentUser.id}")
            name.setText(currentUser.name)
            city.setText(currentUser.city)
            val profilePictureRef = FirebaseStorage.getInstance().reference.child("profilePictures/${currentUser.id}")
            retrieveImageFromFirebaseStorage(this,"profile_picture", profilePicture)

            val bannerRef = FirebaseStorage.getInstance().reference.child("banners/${currentUser.id}")
            Log.d(TAG, "loadUserInformation: $bannerRef")
            retrieveImageFromFirebaseStorage(this,"banner", banner)

        }
    }



    private fun retrieveImageFromFirebaseStorage(context: Context, imageType: String, imageView: ImageView) {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserUid != null) {
            // Define the reference to the image in Firebase Storage
            val imageRef = storageReference.child("profile_images").child("$currentUserUid/$imageType.jpg")

            // Get the download URL of the image
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                // Load the current version or timestamp from Firebase Realtime Database or Firestore
                // Compare it with the locally stored version or timestamp
                val localVersion = 12345L // Retrieve the locally stored version or timestamp
                val backendVersion = 67890L // Retrieve the current version or timestamp from Firebase

                if (localVersion != backendVersion) {
                    // Versions differ, fetch the updated image from Firebase Storage and update cache
                    Picasso.get().load(uri)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(imageView)
                } else {
                    // Versions match, load the image from cache
                    Picasso.get().load(uri)
                        .memoryPolicy(MemoryPolicy.NO_STORE)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageView)
                }
            }.addOnFailureListener { e ->
                // Handle any errors that occur during download
                Log.e("RetrieveImage", "Failed to retrieve image: $e")
            }
        }
    }



    private fun uploadImageToFirebaseStorage(imageUri: Uri, imageType: String) {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserUid != null) {
            // Define path for the image in Firebase Storage
            val filePath =
                storageReference.child("profile_images").child("$currentUserUid/$imageType.jpg")

            // Upload image to Firebase Storage
            filePath.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Image uploaded successfully, get the download URL
                    filePath.downloadUrl.addOnSuccessListener { uri ->
                        // Save the download URL to Firebase Realtime Database or Firestore
                        saveImageUrlToDatabase(uri.toString(), imageType)
                    }
                }
                .addOnFailureListener { e ->
                    // Image upload failed, handle the error
                    Log.e("UploadImage", "Failed to upload image: $e")
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Failed to upload image",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun saveImageUrlToDatabase(imageUrl: String, imageType: String) {
        // Save the image URL to Firebase Realtime Database or Firestore under the user's profile
        // For example, if you're using Realtime Database:
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        if (currentUserUid != null) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUserUid)
            val imageField = if (imageType == "profile_picture") "profilePictureUrl" else "bannerImageUrl"
            databaseReference.child(imageField).setValue(imageUrl)
                .addOnSuccessListener {
                    Snackbar.make(findViewById(android.R.id.content), "Image uploaded successfully", Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e("SaveImageUrl", "Failed to save image URL to database: $e")
                    Snackbar.make(findViewById(android.R.id.content), "Failed to save image URL", Snackbar.LENGTH_SHORT).show()
                }
        }
    }

    private val pickImageGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            try {
                selectedImageUri?.let { uri ->
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    // Set the selected image to the corresponding ImageView
                    when (selectedImageRequestCode) {
                        REQUEST_CODE_PROFILE_PICTURE -> {
                            profilePicture.setImageBitmap(bitmap)
                            uploadImageToFirebaseStorage(uri, "profile_picture")
                        }
                        REQUEST_CODE_BANNER -> {
                            banner.setImageBitmap(bitmap)
                            uploadImageToFirebaseStorage(uri, "banner")
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Snackbar.make(findViewById(android.R.id.content), "Error loading image", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    private fun editProfilePicture() {
        selectedImageRequestCode = REQUEST_CODE_PROFILE_PICTURE
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageGalleryLauncher.launch(galleryIntent)

    }

    private fun editProfileBanner() {
        selectedImageRequestCode = REQUEST_CODE_BANNER
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageGalleryLauncher.launch(galleryIntent)
    }

    companion object {
        private const val REQUEST_CODE_PROFILE_PICTURE = 1
        private const val REQUEST_CODE_BANNER = 2
    }
}