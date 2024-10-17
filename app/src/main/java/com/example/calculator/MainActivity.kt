package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputField: EditText
    private var currentNumber = ""
    private var firstNumber = ""
    private var operator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputField = findViewById(R.id.inputField)

        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonSubtract: Button = findViewById(R.id.buttonSubtract)
        val buttonMultiply: Button = findViewById(R.id.buttonMultiply)
        val buttonDivide: Button = findViewById(R.id.buttonDivide)
        val buttonClear: Button = findViewById(R.id.buttonClear)
        val buttonEqual: Button = findViewById(R.id.buttonEqual)

        // Define click listeners for numeric buttons
        button0.setOnClickListener { appendNumber("0") }
        button1.setOnClickListener { appendNumber("1") }
        button2.setOnClickListener { appendNumber("2") }
        button3.setOnClickListener { appendNumber("3") }
        button4.setOnClickListener { appendNumber("4") }
        button5.setOnClickListener { appendNumber("5") }
        button6.setOnClickListener { appendNumber("6") }
        button7.setOnClickListener { appendNumber("7") }
        button8.setOnClickListener { appendNumber("8") }
        button9.setOnClickListener { appendNumber("9") }

        // Define click listeners for operations
        buttonAdd.setOnClickListener { setOperator("+") }
        buttonSubtract.setOnClickListener { setOperator("-") }
        buttonMultiply.setOnClickListener { setOperator("*") }
        buttonDivide.setOnClickListener { setOperator("/") }

        // Define click listeners for clear and equals
        buttonClear.setOnClickListener { clear() }
        buttonEqual.setOnClickListener { calculateResult() }
    }

    private fun appendNumber(number: String) {
        currentNumber += number
        inputField.setText(currentNumber)
    }

    private fun setOperator(op: String) {
        firstNumber = currentNumber
        operator = op
        currentNumber = ""
    }

    private fun clear() {
        currentNumber = ""
        firstNumber = ""
        operator = ""
        inputField.setText("")
    }

    private fun calculateResult() {
        val secondNumber = currentNumber
        var result = 0.0

        try {
            when (operator) {
                "+" -> result = firstNumber.toDouble() + secondNumber.toDouble()
                "-" -> result = firstNumber.toDouble() - secondNumber.toDouble()
                "*" -> result = firstNumber.toDouble() * secondNumber.toDouble()
                "/" -> {
                    if (secondNumber == "0") {
                        inputField.setText("Error")
                        return
                    } else {
                        result = firstNumber.toDouble() / secondNumber.toDouble()
                    }
                }
            }
            inputField.setText(result.toString())
        } catch (e: Exception) {
            inputField.setText("Error")
        }
    }
}
