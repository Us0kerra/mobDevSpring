package com.example.firststeps

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.floor
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var aInputTextView: TextView
    private lateinit var bInputTextView: TextView
    private lateinit var cInputTextView: TextView
    private lateinit var outputTextView: TextView

    private fun integersNotCorrect(a: Double, b: Double, c:Double): Boolean{
        val D: Double = b*b - 4*a*c
        println(a.toInt() == 0)
        println(D<0)
        println(floor(sqrt(D)) != sqrt(D))
        return (D < 0) || (floor(sqrt(D)) != sqrt(D)) || (a.toInt() == 0 && b.toInt() == 0) || (a.toInt() == 0 && b.toInt() == 0 && c.toInt() == 0)
    }

    fun calcQuadro(a: Double, b: Double, c:Double): String{
        var result1:Double = 0.0
        var result2:Double = 0.0

        if (!integersNotCorrect(a,b,c)){
            if (a.toInt() == 0){
                val result = -c / b
                return "x = $result"
            }
            val D = b*b - 4*a*c
            result1 = (-b + sqrt(D)) / (2*a)
            result2 = (-b - sqrt(D)) / (2*a)
            return "x1 = $result1, x2 = $result2"
        }
        return "Введены некорректные числа"
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

        aInputTextView = findViewById(R.id.editTextA)
        bInputTextView = findViewById(R.id.editTextB)
        cInputTextView = findViewById(R.id.editTextC)

        val button: Button = findViewById(R.id.button)
        val alert1 = AlertDialog.Builder(this).setPositiveButton("Понял", { d, id->d.cancel() } )

        button.setOnClickListener{
            var aInput = aInputTextView.text.toString().replace(',','.').toDouble()
            var bInput = bInputTextView.text.toString().replace(',','.').toDouble()
            var cInput = cInputTextView.text.toString().replace(',','.').toDouble()
            var quadroResult = calcQuadro(aInput,bInput,cInput)

            alert1.setMessage(quadroResult).create()
            alert1.show()
        }
    }



}