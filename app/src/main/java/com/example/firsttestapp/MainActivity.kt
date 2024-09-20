package com.example.firsttestapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val signInButton = findViewById<Button>(R.id.singin)
        signInButton.setOnClickListener {
            val login = findViewById<EditText>(R.id.login)
            val password = findViewById<EditText>(R.id.password)
            if (password.text.toString() == "admin" && login.text.toString() == "admin") {
                val textView = findViewById<TextView>(R.id.result)
                textView.visibility = View.VISIBLE
            }
        }
    }
}