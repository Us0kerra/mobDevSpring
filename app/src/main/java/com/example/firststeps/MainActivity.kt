package com.example.firststeps

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var display: TextView
    private var isError = false
    private val defaultTextSize = 80f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        display.textSize = defaultTextSize
        display.text = "0"

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonPlus, R.id.buttonMinus, R.id.buttonMultiply, R.id.buttonDivide,
            R.id.buttonDot
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onInput(it as Button) }
        }

        findViewById<Button>(R.id.buttonAC).setOnClickListener { clear() }
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.buttonPlusMinus).setOnClickListener { toggleSign() }
    }

    private fun onInput(button: Button) {
        if (display.text.contains("Ошибка") && isError) {
            display.text = "Ошибка"
            isError = false
        }
        if (display.text.toString() == "0") {
            display.text = ""
        }
        display.append(button.text)
        adjustTextSize()
    }

    private fun calculate() {
        try {
            val result = evaluateExpression(display.text.toString())
            display.text = formatResult(result).replace("Infinity", "Ошибка")
        } catch (e: Exception) {
            display.text = "Ошибка"
            isError = true
        }
        adjustTextSize()
    }

    private fun evaluateExpression(expression: String): Double {
        val sanitizedExpression = expression.replace("÷", "/").replace("×", "*")
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < sanitizedExpression.length) sanitizedExpression[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < sanitizedExpression.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.code) -> x += parseTerm()
                        eat('-'.code) -> x -= parseTerm()
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.code) -> x *= parseFactor()
                        eat('/'.code) -> x /= parseFactor()
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor()
                if (eat('-'.code)) return -parseFactor()
                var x: Double
                val startPos = pos
                if (eat('('.code)) {
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = sanitizedExpression.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }
                return x
            }
        }.parse()
    }

    private fun toggleSign() {
        if (isError || display.text.isEmpty()) return

        val text = display.text.toString()

        // Ищем последнее число в строке
        val regex = """(\(?-?\d+(,\d+)?\)?)""".toRegex()  // Регулярное выражение для чисел (целых и с плавающей точкой)
        val match = regex.findAll(text).toList().lastOrNull()  // Получаем последнее найденное число

        if (match != null) {
            var number = match.value

            // Если число обрамлено в скобки, удаляем скобки
            if (number.startsWith("(") && number.endsWith(")")) {
                number = number.substring(1, number.length - 1)
            }

            // Меняем знак числа
            val newNumber = if (number.startsWith("-")) number.substring(1) else "-$number"

            // Если новое число без отрицательного знака, скобки не нужны
            val numberInBrackets = if (newNumber.startsWith("-")) "($newNumber)" else newNumber

            // Заменяем только последнее число
            display.text = text.replaceRange(match.range, numberInBrackets)
        }

        adjustTextSize()
    }


    private fun clear() {
        display.text = "0"
        isError = false
        display.textSize = defaultTextSize
    }

    private fun formatResult(result: Double): String {
        return if (result % 1 == 0.0) result.toInt().toString() else "%.10f".format(result).trimEnd('0')
    }

    private fun adjustTextSize() {
        val length = display.text.length
        display.textSize = when {
            length > 12 -> 36f
            length > 6 -> 48f
            else -> defaultTextSize
        }
    }
}