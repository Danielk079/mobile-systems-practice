package com.danielk.calculator_project2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var inputNum1: EditText
    private lateinit var inputNum2: EditText
    private lateinit var resultText: TextView

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
        inputNum1 = findViewById(R.id.inputNum1)
        inputNum2 = findViewById(R.id.inputNum2)
        resultText = findViewById(R.id.resultText)

        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnSubtract: Button = findViewById(R.id.btnSubtract)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnDivide: Button = findViewById(R.id.btnDivide)

        btnAdd.setOnClickListener { calculate('+') }
        btnSubtract.setOnClickListener { calculate('-') }
        btnMultiply.setOnClickListener { calculate('*') }
        btnDivide.setOnClickListener { calculate('/') }
    }

    private fun calculate(operation: Char) {
        val num1Text = inputNum1.text.toString()
        val num2Text = inputNum2.text.toString()

        if (num1Text.isEmpty() || num2Text.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val num1 = num1Text.toDoubleOrNull()
        val num2 = num2Text.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val result: Double = when (operation) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> {
                if (num2 == 0.0) {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                }
                num1 / num2
            }
            else -> 0.0
        }

        resultText.text = "Result: ${formatResult(result)}"
    }

    private fun formatResult(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}