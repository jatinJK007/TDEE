package com.example.tdeecalc

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_display)

        val tvbmr = findViewById<TextView>(R.id.bmr)
        val tvbmi = findViewById<TextView>(R.id.bmi)
        val tvtdee = findViewById<TextView>(R.id.tdee)

        val bmr = intent.getDoubleExtra("BMR", 0.0)
        val bmi = intent.getDoubleExtra("BMI", 0.0)
        val tdee = intent.getDoubleExtra("TDEE", 0.0)
        Log.d("TAG", "onCreate: in display ${bmi} and ${tvbmr} ")
        tvbmr.text = "BMR: %.2f".format(bmr)
        tvbmi.text = "BMI :%.2f".format(bmi)
        tvtdee.text = "TDEE: %.2f".format(tdee)
    }
}