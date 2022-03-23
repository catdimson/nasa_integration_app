package ru.dkotik.nasaintegrationapp.view.chips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.databinding.FragmentChipsBinding

class ChipsFragment: Fragment() {
    private var _binding: FragmentChipsBinding? = null
    val binding: FragmentChipsBinding
        get () = _binding!!

    companion object {
        fun newInstance() = ChipsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}