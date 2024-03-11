package com.muhammadfarazrashid.i2106595

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddAMentor : AppCompatActivity() {

    private lateinit var uploadPhotoButton: Button
    private lateinit var name: EditText
    private lateinit var description: EditText
    private lateinit var availabilitySpinner: Spinner
    private lateinit var sessionPrice: EditText
    private lateinit var position: EditText
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference
    private var mentorId: String = ""

    //make databse global
    private val database= FirebaseDatabase.getInstance()
    private val myRef=database.getReference("Mentors")
    private lateinit var selectedImageUri: Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addmentorpage)

        uploadPhotoButton = findViewById(R.id.uploadPhotoButton)
        name = findViewById(R.id.userNameTextBox)
        description = findViewById(R.id.userDescriptionBox)
        sessionPrice = findViewById(R.id.userHourlyRateBox)
        position = findViewById(R.id.userPositionBox)
        setUpSpinners()

        mentorId=myRef.push().key.toString()

        uploadPhotoButton.setOnClickListener {
            uploadPhoto(mentorId)
        }


        val imageView11 = findViewById<ImageView>(R.id.imageView11)
        imageView11.setOnClickListener {
            onBackPressed()
        }

        //click on signupbutton2 and go back to homepage

        val signupbutton2 = findViewById<Button>(R.id.signupButton2)
        signupbutton2.setOnClickListener {

            addAMentor()
        }

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
    }

    private fun setUpSpinners() {
        availabilitySpinner = findViewById(R.id.userContactNumberSpinner)
        val availabilityList= arrayOf("Select An Availability", "Available", "Not Available")
        val countryAdapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            availabilityList
        ) {
            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner (use for hint)
                return position != 0
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                if (position == 0) {
                    tv.setTextColor(Color.GRAY)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return view
            }
        }
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        availabilitySpinner.adapter = countryAdapter

        availabilitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItemText = parent?.getItemAtPosition(position).toString()

                if (position > 0) {

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


    }

    private fun verifyData(): Boolean {
        if (name.text.isEmpty()) {
            name.error = "Name is required"
            name.requestFocus()
            return false
        }
        if (description.text.isEmpty()) {
            description.error = "Description is required"
            description.requestFocus()
            return false
        }
        if (availabilitySpinner.selectedItemPosition == 0) {
            (availabilitySpinner.selectedView as TextView).error = "Availability is required"
            availabilitySpinner.requestFocus()
            return false
        }
        if (sessionPrice.text.isEmpty()) {
            sessionPrice.error = "Session Price is required"
            sessionPrice.requestFocus()
            return false
        }
        if (position.text.isEmpty()) {
            position.error = "Position is required"
            position.requestFocus()
            return false
        }
        if (selectedImageUri == null) {
            Snackbar.make(findViewById(android.R.id.content), "Profile picture is required", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }


        private fun uploadMentorProfilePicture(imageUri: Uri, mentorId: String) {

            // Define path for the image in Firebase Storage

            val filePath = storageReference.child("mentor_profile_images").child("$mentorId.jpg")
            Log.d("UploadImage", "Uploading image to: ${filePath.path}")
    // Upload image to Firebase Storage
            filePath.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Image uploaded successfully, get the download URL
                    filePath.downloadUrl.addOnSuccessListener { uri ->
                        // Save the download URL to Firebase Realtime Database or Firestore
                        saveMentorProfilePictureUrlToDatabase(mentorId, uri.toString())
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

    private fun sanitizeId(id: String): String {
        var sanitizedId = id.replace(".", "a")
            .replace("#", "a")
            .replace("$", "a")
            .replace("[", "a")
            .replace("]", "a")

        // Remove the dash if it exists at the beginning
        if (sanitizedId.startsWith("-")) {
            sanitizedId = sanitizedId.substring(1)
        }

        return sanitizedId
    }



    @SuppressLint("SuspiciousIndentation")
    private fun saveMentorProfilePictureUrlToDatabase(mentorId: String, imageUrl: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Mentors").child(mentorId)
            databaseReference.child("profilePictureUrl").setValue(imageUrl)
                .addOnSuccessListener {
                    Snackbar.make(findViewById(android.R.id.content), "Profile picture uploaded successfully", Snackbar.LENGTH_SHORT).show()
                    val intent = Intent(this, homePageActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Log.e("SaveImageUrl", "Failed to save profile picture URL to database: $e")
                    Snackbar.make(findViewById(android.R.id.content), "Failed to save profile picture URL", Snackbar.LENGTH_SHORT).show()
                }

    }

    private val pickImageGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data ?: return@registerForActivityResult

        }
    }

    private fun uploadPhoto(mentorId:String ) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageGalleryLauncher.launch(galleryIntent)
    }


    fun addAMentor() {
        if(!verifyData()) return

        val mentor = Mentor(sanitizeId(mentorId), name.text.toString(), position.text.toString(), availabilitySpinner.selectedItem.toString(), sessionPrice.text.toString(), description.text.toString())
        Log.d("sanitizedId", sanitizeId(mentorId))
        val mentorId=sanitizeId(mentorId)
        myRef.child(mentorId).setValue(mentor)
            .addOnSuccessListener {
                Log.d("AddAMentor", "Mentor added successfully")
                uploadMentorProfilePicture(selectedImageUri, mentorId)

            }
            .addOnFailureListener {
                Log.d("AddAMentor", "Mentor addition failed")
            }

    }
}