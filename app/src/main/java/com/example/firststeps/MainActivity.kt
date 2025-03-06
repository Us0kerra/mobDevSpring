package com.example.firststeps

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = StringBuilder()
    private var currentOperator: String? = null
    private var firstOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        setNumberButtonListeners()
        setOperatorButtonListeners()
        setSpecialButtonListeners()
    }

    private fun setNumberButtonListeners() {
        val numberButtons = listOf(
            findViewById<Button>(R.id.buttonZero),
            findViewById<Button>(R.id.buttonOne),
            findViewById<Button>(R.id.buttonTwo),
            findViewById<Button>(R.id.buttonThree),
            findViewById<Button>(R.id.buttonFour),
            findViewById<Button>(R.id.buttonFive),
            findViewById<Button>(R.id.buttonSix),
            findViewById<Button>(R.id.buttonSeven),
            findViewById<Button>(R.id.buttonEight),
            findViewById<Button>(R.id.buttonNine),
            findViewById<Button>(R.id.buttonDot)
        )

        numberButtons.forEach { button ->
            button.setOnClickListener {
                currentInput.append(button.text)
                updateDisplay()
            }
        }
    }

    private fun setOperatorButtonListeners() {
        val operatorButtons = listOf(
            findViewById<Button>(R.id.buttonPlus),
            findViewById<Button>(R.id.buttonMinus),
            findViewById<Button>(R.id.buttonMultiply),
            findViewById<Button>(R.id.buttonDivide)
        )

        operatorButtons.forEach { button ->
            button.setOnClickListener {
                if (currentInput.isNotEmpty()) {
                    firstOperand = currentInput.toString().toDouble()
                    currentOperator = button.text.toString()
                    currentInput.clear()
                    updateDisplay()
                }
            }
        }
    }

    private fun setSpecialButtonListeners() {
        findViewById<Button>(R.id.buttonAC).setOnClickListener {
            currentInput.clear()
            firstOperand = null
            currentOperator = null
            display.text = "0"
        }

        findViewById<Button>(R.id.buttonPlusMinus).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                val value = currentInput.toString().toDouble()
                currentInput.clear()
                currentInput.append(-value)
                updateDisplay()
            }
        }

        findViewById<Button>(R.id.buttonPercent).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                val value = currentInput.toString().toDouble()
                currentInput.clear()
                currentInput.append(value / 100)
                updateDisplay()
            }
        }

        findViewById<Button>(R.id.buttonEquals).setOnClickListener {
            if (currentInput.isNotEmpty() && firstOperand != null && currentOperator != null) {
                val secondOperand = currentInput.toString().toDouble()
                val result = when (currentOperator) {
                    "+" -> firstOperand!! + secondOperand
                    "-" -> firstOperand!! - secondOperand
                    "*" -> firstOperand!! * secondOperand
                    "/" -> if (secondOperand != 0.0) firstOperand!! / secondOperand else Double.NaN
                    else -> Double.NaN
                }
                currentInput.clear()
                currentInput.append(if (result.isNaN()) "Error" else result)
                updateDisplay()
                firstOperand = null
                currentOperator = null
            }
        }
    }

    private fun updateDisplay() {
        display.text = currentInput.toString().ifEmpty { "0" }
    }
}