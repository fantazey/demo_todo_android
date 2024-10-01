package com.example.firsttestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.firsttestapp.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    lateinit var authViewModel: AuthViewModel
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.singin.setOnClickListener {
            if (authViewModel.authUser(binding.login.toString(), binding.password.toString())) {
                val action = AuthFragmentDirections.actionSuccessLogin(authViewModel.username)
                view.findNavController().navigate(action)
            } else {
                binding.result.visibility = View.VISIBLE
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}