package com.example.firsttestapp

import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    private var login = null
    private var password = null

    lateinit var username: String
    private lateinit var groups: List<String>

    fun authUser(login: String, password: String): Boolean {
        if (login == password) {
            username = login
            groups = listOf("user", "test")
            return true
        }
        return false
    }
}