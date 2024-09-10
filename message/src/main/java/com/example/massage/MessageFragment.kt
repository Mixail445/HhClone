package com.example.massage

import androidx.fragment.app.Fragment
import com.example.massage.databinding.FragmentMessageBinding

class MessageFragment: Fragment(R.layout.fragment_message) {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
}