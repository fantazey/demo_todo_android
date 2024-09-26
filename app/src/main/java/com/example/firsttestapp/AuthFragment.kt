package com.example.firsttestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController


class AuthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)
        val signInButton = view.findViewById<Button>(R.id.singin)

        signInButton.setOnClickListener {
            val login = view.findViewById<EditText>(R.id.login)
            val password = view.findViewById<EditText>(R.id.password)
            if (password.text.toString() ==  login.text.toString()) {
                val action = AuthFragmentDirections.actionSuccessLogin(login.text.toString())
                view.findNavController().navigate(action)
            } else {
                val textView = view.findViewById<TextView>(R.id.result)
                textView.visibility = View.VISIBLE
            }
        }
        return view
    }
}