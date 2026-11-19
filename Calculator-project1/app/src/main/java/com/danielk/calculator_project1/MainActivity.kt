package com.danielk.calculator_project1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- calculator logic starts here ---
        val inputNum1: EditText = findViewById(R.id.inputNum1)
        val inputNum2: EditText = findViewById(R.id.inputNum2)
        val resultText: TextView = findViewById(R.id.resultText)
        val btnAdd: Button = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val num1 = inputNum1.text.toString().toDouble()
            val num2 = inputNum2.text.toString().toDouble()
            val sum = num1 + num2
            resultText.text = "Result: $sum"
        }

    }
}