package com.muhammadfarazrashid.i2106595

import UserManager
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class reviewpage: AppCompatActivity() {

    private lateinit var name: TextView
    private lateinit var currentMentor: Mentor
    private lateinit var mentorImageView: ImageView
    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView
    private lateinit var star4: ImageView
    private lateinit var star5: ImageView
    private var starRating: Float = 0f
    private var database = FirebaseDatabase.getInstance()
    private lateinit var reviewText : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reviewpage)

        val mentor = intent.getParcelableExtra<Mentor>("mentor")

        // Log mentor details to verify if data is received correctly
        mentor?.let {
            with(it) {
                logMentorDetails(id, name, position, availability, salary, description, isFavorite)
            }
        }

        initViews()

        // Set mentor details
        mentor?.let { setMentorDetails(it) }


        setUpOnClickListeners()

    }

    private fun Mentor.logMentorDetails(
        id: String?,
        name: String?,
        position: String?,
        availability: String?,
        salary: String?,
        description: String?,
        isFavorite: Boolean
    ) {
        id?.let { Log.d("AboutMentorPage", "Mentor ID: $it") }
        name?.let { Log.d("AboutMentorPage", "Mentor Name: $it") }
        position?.let { Log.d("AboutMentorPage", "Mentor Position: $it") }
        availability?.let { Log.d("AboutMentorPage", "Mentor Availability: $it") }
        salary?.let { Log.d("AboutMentorPage", "Mentor Salary: $it") }
        description?.let { Log.d("AboutMentorPage", "Mentor Description: $it") }
        Log.d("AboutMentorPage", "Mentor isFavorite: $isFavorite")
    }

    fun initViews() {
        name = findViewById(R.id.mentorName)
        mentorImageView = findViewById(R.id.imageView9)
        reviewText = findViewById(R.id.reviewText)
        star1 = findViewById<ImageView>(R.id.star1)
        star2 = findViewById<ImageView>(R.id.star2)
        star3 = findViewById<ImageView>(R.id.star3)
        star4 = findViewById<ImageView>(R.id.star4)
        star5 = findViewById<ImageView>(R.id.star5)
    }

    private fun setMentorDetails(mentor: Mentor) {
        name.text = mentor.name
        currentMentor = mentor

        if (mentor.getprofilePictureUrl().isNotEmpty()) {
            Picasso.get().load(mentor.getprofilePictureUrl()).into(mentorImageView)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(imageView: ImageView, starNumber: Int) {
        imageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Calculate if the touch is on the left or right half of the star
                    val isLeftHalf = event.x < imageView.width / 2
                    // Calculate the rating based on left or right half
                    starRating = if (isLeftHalf) {
                        starNumber - 0.5f
                    } else {
                        starNumber.toFloat()
                    }
                    // Update star rating
                    updateStars(starRating)
                    true
                }

                else -> false
            }
        }
    }

    private fun updateStars(rating: Float) {
        val star1 = findViewById<ImageView>(R.id.star1)
        val star2 = findViewById<ImageView>(R.id.star2)
        val star3 = findViewById<ImageView>(R.id.star3)
        val star4 = findViewById<ImageView>(R.id.star4)
        val star5 = findViewById<ImageView>(R.id.star5)

        // Reset all stars
        star1.setImageResource(R.drawable.emptystar)
        star2.setImageResource(R.drawable.emptystar)
        star3.setImageResource(R.drawable.emptystar)
        star4.setImageResource(R.drawable.emptystar)
        star5.setImageResource(R.drawable.emptystar)

        // Set stars according to rating
        when {
            rating >= 1 -> {
                star1.setImageResource(R.drawable.star)
                if (rating == 1.5f) {
                    star2.setImageResource(R.drawable.halfstar)
                } else if (rating > 1.5f) {
                    star2.setImageResource(R.drawable.star)
                }
            }
            rating > 0.5f -> star1.setImageResource(R.drawable.halfstar)
        }
        when {
            rating >= 2 -> {
                star2.setImageResource(R.drawable.star)
                if (rating == 2.5f) {
                    star3.setImageResource(R.drawable.halfstar)
                } else if (rating > 2.5f) {
                    star3.setImageResource(R.drawable.star)
                }
            }
            rating > 1.5f -> star2.setImageResource(R.drawable.halfstar)
        }
        when{
            rating >= 3 -> {
                star3.setImageResource(R.drawable.star)
                if (rating == 3.5f) {
                    star4.setImageResource(R.drawable.halfstar)
                } else if (rating > 3.5f) {
                    star4.setImageResource(R.drawable.star)
                }
            }
            rating > 2.5f -> star3.setImageResource(R.drawable.halfstar)
        }
        when {
            rating >= 4 -> {
                star4.setImageResource(R.drawable.star)
                if (rating == 4.5f) {
                    star5.setImageResource(R.drawable.halfstar)
                } else if (rating > 4.5f) {
                    star5.setImageResource(R.drawable.star)
                }
            }
            rating > 3.5f -> star4.setImageResource(R.drawable.halfstar)
        }
        when {
            rating >= 5 -> {
                star5.setImageResource(R.drawable.star)
            }
            rating > 4.5f -> star5.setImageResource(R.drawable.halfstar)
        }
    }


    private fun setUpOnClickListeners() {

        setTouchListener(star1, 1)
        setTouchListener(star2, 2)
        setTouchListener(star3, 3)
        setTouchListener(star4, 4)
        setTouchListener(star5, 5)


        val imageView4 = findViewById<ImageView>(R.id.imageView4)
        imageView4.setOnClickListener {
            onBackPressed()
        }

        //click on signup button and go back to home page

        val imageView5 = findViewById<Button>(R.id.signupButton)

        imageView5.setOnClickListener {
            submitAndUploadFeedback(starRating, reviewText.text.toString())
        }

    }

    data class userReview(val mentorId:String,val mentorName:String, val rating: Float, val reviewText: String)

    //add another review constructor without mentorid
    data class mentorReview(val rating: Float, val reviewText: String)

    fun submitAndUploadFeedback(rating: Float, reviewText: String) {
        if (::currentMentor.isInitialized) {
            // Fetch current mentor's data from the database
            if(currentMentor.id.startsWith("-"))
            {
                currentMentor.id = currentMentor.id.substring(1)
            }

            val mentorRef = database.getReference("Mentors").child(currentMentor.id ?: "")

            // Fetch current rating and number of ratings
            mentorRef.child("currentRating").get().addOnSuccessListener { currentRatingSnapshot ->
                val currentRating = currentRatingSnapshot.getValue(Float::class.java)
                Log.d("submitFeedback", "Current rating: $currentRating")

                mentorRef.child("numberOfRatings").get().addOnSuccessListener { numberOfRatingsSnapshot ->
                    val numberOfRatings = numberOfRatingsSnapshot.getValue(Int::class.java)
                    Log.d("submitFeedback", "Number of ratings: $numberOfRatings")

                    // Calculate new rating
                    val newRating = if (currentRating != null && numberOfRatings != null && numberOfRatings > 0) {
                        ((numberOfRatings * currentRating) + rating) / (numberOfRatings + 1)
                    } else {
                        rating
                    }
                    Log.d("submitFeedback", "New rating: $newRating")

                    // Update mentor's data in the database
                    mentorRef.updateChildren(
                        mapOf(
                            "currentRating" to newRating,
                            "numberOfRatings" to (numberOfRatings ?: 0) + 1
                        )
                    ).addOnSuccessListener {
                        Log.d("submitFeedback", "Mentor data updated successfully.")

                        // Upload review to mentor
                        mentorRef.child("reviews").push().setValue(mentorReview(rating, reviewText))
                            .addOnSuccessListener {
                                Log.d("submitFeedback", "Review uploaded to mentor.")
                            }.addOnFailureListener {
                                Log.e("submitFeedback", "Failed to upload review to mentor: ${it.message}")
                            }

                        // Upload review to user if user is available
                        UserManager.getInstance().getCurrentUser()?.let { user ->
                            database.getReference("users").child(user.id).child("reviews").push()
                                .setValue(userReview(currentMentor.id, currentMentor.name, rating, reviewText))
                                .addOnSuccessListener {
                                    Log.d("submitFeedback", "Review uploaded to user.")
                                }.addOnFailureListener {
                                    Log.e("submitFeedback", "Failed to upload review to user: ${it.message}")
                                }
                        }

                        // Navigate to the home page
                        val intent = Intent(this, homePageActivity::class.java)
                        startActivity(intent)
                    }.addOnFailureListener {
                        Log.e("submitFeedback", "Failed to update mentor data: ${it.message}")
                    }
                }.addOnFailureListener {
                    Log.e("submitFeedback", "Failed to get number of ratings: ${it.message}")
                }
            }.addOnFailureListener {
                Log.e("submitFeedback", "Failed to get current rating: ${it.message}")
            }
        } else {
            Log.e("submitFeedback", "Current mentor is not initialized.")
        }
    }



}


