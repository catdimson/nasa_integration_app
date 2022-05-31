package ru.dkotik.nasaintegrationapp.view.ux

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.databinding.FragmentUxBinding

class UXFragment: Fragment() {

    private var _binding: FragmentUxBinding? = null
    private val binding: FragmentUxBinding
        get() = _binding!!

    companion object{
        fun newInstance() = UXFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUxBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}