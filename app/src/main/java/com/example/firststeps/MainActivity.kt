package com.example.firststeps

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextEdit: TextView
    private lateinit var outputTextView: TextView

    fun textIsCorrect(text: CharSequence): CharSequence{
        if (text.isEmpty()){return "Введи пожалуйста корректный текст :("}
        else if (text.trim().isEmpty()){return "Это пробелы :( Введи, пожалуйста, текст"}
        return text
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

        inputTextEdit = findViewById(R.id.editTextText)
        outputTextView = findViewById(R.id.textView)

        var imageButton: ImageButton = findViewById(R.id.imageButton)

        imageButton.setOnClickListener{
            outputTextView.text = textIsCorrect(inputTextEdit.text)
            inputTextEdit.text = ""
        }

    }

//    fun transferText(){
//        //Допустим тап кнопку
//        var textToTransfer = editTextText.text // присваиваем переменной текст из поля ввода
//        editTextText.text = "Не будь бякой, введи текст ^_^" // возвращаем дефолт текст в поле ввода
//        textView.text = textToTransfer // Присваиваем нижнему лейблу наш текст
//    }
}