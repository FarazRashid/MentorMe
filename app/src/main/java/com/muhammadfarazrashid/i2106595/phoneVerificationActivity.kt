package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class phoneVerificationActivity : AppCompatActivity() {

    private lateinit var circle1: ImageView
    private lateinit var circle2: ImageView
    private lateinit var circle3: ImageView
    private lateinit var circle4: ImageView
    private lateinit var circle5: ImageView

    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var backbutton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verifyphonenumber)

        circle1 = findViewById(R.id.circle1)
        circle2 = findViewById(R.id.circle2)
        circle3 = findViewById(R.id.circle3)
        circle4 = findViewById(R.id.circle4)
        circle5 = findViewById(R.id.circle5)

        // Find buttons by their IDs
        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        button8 = findViewById(R.id.button8)
        button9 = findViewById(R.id.button9)
        backbutton = findViewById(R.id.backbutton)


        // Initialize an array to store the circles ImageViews
        val circleArray = arrayOf(circle1, circle2, circle3, circle4, circle5)

        // Initialize a StringBuilder to store the entered code
        val enteredCode = StringBuilder()

        // Set OnClickListener for each number button
        val numberButtons = arrayOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        for (button in numberButtons) {
            button.setOnClickListener {
                // Check if the entered code has reached the maximum length
                if (enteredCode.length < circleArray.size) {
                    // Append the clicked number to the entered code
                    enteredCode.append((it as Button).text)

                    // Update the circles with the entered code
                    updateCircles(circleArray, enteredCode.toString())
                }
            }
        }

        // Set OnClickListener for the back button
        backbutton.setOnClickListener {
            // Remove the last character from the entered code
            if (enteredCode.isNotEmpty()) {
                enteredCode.deleteCharAt(enteredCode.length - 1)

                // Update the circles with the entered code
                updateCircles(circleArray, enteredCode.toString())
            }
        }

        //click on verify go to homepage

        val verifyButton: Button = findViewById(R.id.verifyButton)
        verifyButton.setOnClickListener {
            val intent = Intent(this, homePageActivity::class.java)
            startActivity(intent)
            finish()
        }

        //click on imageview 4 go back to sign up

        val backToSignUp: ImageView = findViewById(R.id.imageView4)
        backToSignUp.setOnClickListener {
            val intent = Intent(this, signUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun updateCircles(circleArray: Array<ImageView>, enteredCode: String) {
        // Iterate over all circles and set their content
        for (i in 0 until circleArray.size) {
            val circle = circleArray[i]
            if (i < enteredCode.length) {
                // If there is a number at this position, draw it on the circle
                drawTextOnCircle(circle, enteredCode[i].toString())
            } else {
                // If no number at this position, show an empty circle
                circle.setImageResource(R.drawable.circleoutline)
            }
        }
    }


    private fun drawTextOnCircle(circle: ImageView, text: String) {
        // Create a new Bitmap with the same size as the circle
        val bitmap = Bitmap.createBitmap(circle.width, circle.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Draw the circle background using the existing drawable
        val circleDrawable = ContextCompat.getDrawable(circle.context, R.drawable.circleoutline)
        circleDrawable?.setBounds(0, 0, canvas.width, canvas.height)
        circleDrawable?.draw(canvas)

        // Customize paint properties like color, size, etc.
        val paint = Paint().apply {
            //i want this color #3C8B90
            color = Color.parseColor("#3C8B90")
            textSize = 50f // Adjust the text size as needed
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD // Make the text bold
            style = Paint.Style.FILL
        }

        // Calculate text position to center it in the circle
        val x = canvas.width / 2f
        val y = (canvas.height - (paint.descent() + paint.ascent())) / 2f

        // Draw the text on the canvas
        canvas.drawText(text, x, y, paint)

        // Set the generated Bitmap to the ImageView
        circle.setImageBitmap(bitmap)
    }


}