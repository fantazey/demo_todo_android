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

import com.example.firsttestapp.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root
        val signInButton = binding.singin
        signInButton.setOnClickListener {
            val login = binding.login
            val password = binding.password
            if (password.text.toString() ==  login.text.toString()) {
                val action = AuthFragmentDirections.actionSuccessLogin(login.text.toString())
                view.findNavController().navigate(action)
            } else {
                val textView = binding.result
                textView.visibility = View.VISIBLE
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}