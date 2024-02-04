package com.muhammadfarazrashid.i2106595

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class signUpActivity: AppCompatActivity(){


        private lateinit var userCountrySpinner: Spinner
        private lateinit var userCitySpinner: Spinner

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.signup_page)

            // Initialize the Spinner using findViewById
            userCountrySpinner = findViewById(R.id.userCountryTextBos)

            // Set up the Spinner with data and adapter as needed
            // Example:
            val countryList = listOf("Select A Country", "Country 2", "Country 3")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            userCountrySpinner.adapter = adapter

            // Set a listener for item selection if needed
            userCountrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Handle the selected item
                    val selectedCountry = countryList[position]
                    // Do something with the selected country
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case where nothing is selected
                }
            }

            //do the same for city

            userCitySpinner = findViewById(R.id.userCityTextBox)
            val cityList = listOf("Select A City", "City 2", "City 3")
            val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityList)
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            userCitySpinner.adapter = adapter2
            userCitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    // Handle the selected item
                    val selectedCity = cityList[position]
                    // Do something with the selected city
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Handle the case where nothing is selected
                }
            }

            //click login and go to login page

            val signUpActivity: Button = findViewById(R.id.signupButton)
            signUpActivity.setOnClickListener {
                val intent = Intent(this, phoneVerificationActivity::class.java)
                startActivity(intent)
                finish()
            }

            //click sign up go to phone verification page

            val loginActivity: TextView = findViewById(R.id.textView11)
            loginActivity.setOnClickListener {
                val intent = Intent(this, loginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
}