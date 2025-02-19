package com.example.firststeps

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    var result: String = ""

    private fun buildNumber(text:String){
        val currentNumber = textView.text.toString()
        textView.text = currentNumber + text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    var buttonZero: Button = findViewById(R.id.buttonZero)
    var buttonOne: Button = findViewById(R.id.buttonOne)
    var buttonTwo: Button = findViewById(R.id.buttonTwo)
    var buttonThree: Button = findViewById(R.id.buttonThree)
    var buttonFour: Button = findViewById(R.id.buttonFour)
    var buttonFive: Button = findViewById(R.id.buttonFive)
    var buttonSix: Button = findViewById(R.id.buttonSix)
    var buttonSeven: Button = findViewById(R.id.buttonSeven)
    var buttonEight: Button = findViewById(R.id.buttonEight)
    var buttonNine: Button = findViewById(R.id.buttonNine)



}