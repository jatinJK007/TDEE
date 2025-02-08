package com.example.tdeecalc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize UI components
        val heightInput = findViewById<TextInputEditText>(R.id.heightInput)
        val weightInput = findViewById<TextInputEditText>(R.id.weightInput)
        val ageInput = findViewById<TextInputEditText>(R.id.ageInput)
        val maleRadio = findViewById<RadioButton>(R.id.maleRadio)
        val femaleRadio = findViewById<RadioButton>(R.id.femaleRadio)
        val activitySpinner = findViewById<Spinner>(R.id.activitySpinner)
        val calculateButton = findViewById<Button>(R.id.calculateButton)

        calculateButton.setOnClickListener {
            // Read inputs (replace commas for locale compatibility)
            val height = heightInput.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0
            val weight = weightInput.text.toString().replace(",", ".").toDoubleOrNull() ?: 0.0
            val age = ageInput.text.toString().toIntOrNull() ?: 0
            val isMale = findViewById<RadioButton>(R.id.maleRadio).isChecked
            val isFemale = findViewById<RadioButton>(R.id.femaleRadio).isChecked
            val activityLevel = activitySpinner.selectedItemPosition

            // Validate inputs
            if (height <= 0 || weight <= 0 || age <= 0) {
                Toast.makeText(this, "Please enter valid values!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isMale && !isFemale) {
                Toast.makeText(this, "Please select a gender!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Calculate BMR and TDEE
            val bmr = calculateBMR(height, weight, age, isMale)
            val tdee = calculateTDEE(bmr, activityLevel)

            // Pass data to DisplayActivity
            val intent = Intent(this, DisplayActivity::class.java).apply {
                putExtra("BMI", calculateBMI(weight, height))
                putExtra("BMR", bmr)
                putExtra("TDEE", tdee)
            }
            startActivity(intent)
        }
    }



    // BMI Calculation
    private fun calculateBMI(weight: Double, height: Double): Double {
        val heightInMeters = height / 100 // Convert cm to meters
        return weight / (heightInMeters * heightInMeters)
    }

    // BMR Calculation
    private fun calculateBMR(height: Double, weight: Double, age: Int, isMale: Boolean): Double {
        return if (isMale) {
            88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
        } else {
            447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
        }
    }

    // TDEE Calculation
    private fun calculateTDEE(bmr: Double, activityLevel: Int): Double {
        return when (activityLevel) {
            0 -> bmr * 1.2 // Sedentary
            1 -> bmr * 1.375 // Lightly active
            2 -> bmr * 1.55 // Moderately active
            3 -> bmr * 1.725 // Very active
            4 -> bmr * 1.9 // Extra active
            else -> bmr
        }
    }
}