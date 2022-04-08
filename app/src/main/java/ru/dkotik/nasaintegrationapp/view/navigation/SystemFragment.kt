package ru.dkotik.nasaintegrationapp.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.databinding.FragmentSystemBinding

class SystemFragment: Fragment() {

    private var _binding: FragmentSystemBinding? = null
    val binding: FragmentSystemBinding
        get () = _binding!!


    companion object {
        fun newInstance() = SystemFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}