package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var inputField: EditText
    private var currentExpression = ""

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
        button0.setOnClickListener { appendToExpression("0") }
        button1.setOnClickListener { appendToExpression("1") }
        button2.setOnClickListener { appendToExpression("2") }
        button3.setOnClickListener { appendToExpression("3") }
        button4.setOnClickListener { appendToExpression("4") }
        button5.setOnClickListener { appendToExpression("5") }
        button6.setOnClickListener { appendToExpression("6") }
        button7.setOnClickListener { appendToExpression("7") }
        button8.setOnClickListener { appendToExpression("8") }
        button9.setOnClickListener { appendToExpression("9") }

        // Define click listeners for operations
        buttonAdd.setOnClickListener { appendToExpression("+") }
        buttonSubtract.setOnClickListener { appendToExpression("-") }
        buttonMultiply.setOnClickListener { appendToExpression("*") }
        buttonDivide.setOnClickListener { appendToExpression("/") }

        // Define click listeners for clear and equals
        buttonClear.setOnClickListener { clear() }
        buttonEqual.setOnClickListener { calculateResult() }
    }

    private fun appendToExpression(value: String) {
        currentExpression += value
        inputField.setText(currentExpression)
    }

    private fun clear() {
        currentExpression = ""
        inputField.setText("")
    }

    private fun calculateResult() {
        try {
            val result = eval(currentExpression)
            inputField.setText(result.toString())
            currentExpression = result.toString()
        } catch (e: Exception) {
            inputField.setText("Error")
        }
    }

    private fun eval(expression: String): Double {
        val tokens = expression.toCharArray()

        val values: MutableList<Double> = ArrayList()
        val ops: MutableList<Char> = ArrayList()
        var i = 0
        while (i < tokens.size) {
            if (tokens[i] == ' ') {
                i++
                continue
            }
            if (tokens[i] in '0'..'9' || tokens[i] == '.') {
                val sb = StringBuilder()
                while (i < tokens.size && (tokens[i] in '0'..'9' || tokens[i] == '.')) sb.append(tokens[i++])
                values.add(sb.toString().toDouble())
                i--
            } else if (tokens[i] == '(') {
                ops.add(tokens[i])
            } else if (tokens[i] == ')') {
                while (ops[ops.size - 1] != '(') values.add(applyOp(ops.removeAt(ops.size - 1), values.removeAt(values.size - 1), values.removeAt(values.size - 1)))
                ops.removeAt(ops.size - 1)
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                while (ops.isNotEmpty() && hasPrecedence(tokens[i], ops[ops.size - 1])) values.add(applyOp(ops.removeAt(ops.size - 1), values.removeAt(values.size - 1), values.removeAt(values.size - 1)))
                ops.add(tokens[i])
            }
            i++
        }

        while (ops.isNotEmpty()) values.add(applyOp(ops.removeAt(ops.size - 1), values.removeAt(values.size - 1), values.removeAt(values.size - 1)))
        return values[0]
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        return !(op1 == '*' || op1 == '/') || !(op2 == '+' || op2 == '-')
    }

    private fun applyOp(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b == 0.0) throw UnsupportedOperationException("Cannot divide by zero") else a / b
            else -> 0.0
        }
    }
}
