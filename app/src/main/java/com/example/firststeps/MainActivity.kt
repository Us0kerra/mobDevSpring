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

    private fun integersNotCorrect(a: Double, b: Double, c:Double): Boolean{
        val D: Double = b*b - 4*a*c
        return (D < 0) || (a.toInt() == 0 && b.toInt() == 0) || (a.toInt() == 0 && b.toInt() == 0 && c.toInt() == 0)
    }

    fun uncorrectEnter(a:String,b:String,c:String):Boolean{
        return (a.toDoubleOrNull() == null || b.toDoubleOrNull() == null || c.toDoubleOrNull() == null)
    }

    fun calcQuadro(a: String, b: String, c:String): String{
        var result1:Double = 0.0
        var result2:Double = 0.0

        if (uncorrectEnter(a,b,c)){
            return "Некорректный ввод"
        }

        var aDouble = a.toDouble()
        var bDouble = b.toDouble()
        var cDouble = c.toDouble()


        if (!integersNotCorrect(aDouble,bDouble,cDouble)){
            if (a.toInt() == 0){
                val result = -cDouble / bDouble
                return "Это линейное уравнение. x = $result"
            }
            val D = bDouble*bDouble - 4*aDouble*cDouble
            result1 = (-bDouble + sqrt(D)) / (2*aDouble)
            result2 = (-bDouble - sqrt(D)) / (2*aDouble)
            return "x1 = $result1, x2 = $result2"
        }
        return "Корней нет"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        aInputTextView = findViewById(R.id.editTextA)
        bInputTextView = findViewById(R.id.editTextB)
        cInputTextView = findViewById(R.id.editTextC)

        val button: Button = findViewById(R.id.button)
        val alert1 = AlertDialog.Builder(this).setPositiveButton("Понял", { d, id->d.cancel() } )

        button.setOnClickListener{
            var aInput = aInputTextView.text.toString().replace(',','.')
            var bInput = bInputTextView.text.toString().replace(',','.')
            var cInput = cInputTextView.text.toString().replace(',','.')
            var quadroResult = calcQuadro(aInput,bInput,cInput)

            alert1.setMessage(quadroResult).create()
            alert1.show()
        }
    }



}