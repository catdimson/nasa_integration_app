package ru.dkotik.nasaintegrationapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.databinding.FragmentEpicEightBinding
import ru.dkotik.nasaintegrationapp.view.main.behavior.ButtonBehavior

class EpicEarthFragment: Fragment() {

    private var _binding: FragmentEpicEightBinding? = null
    private val binding: FragmentEpicEightBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpicEightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val behavior = ButtonBehavior(requireContext())
        (binding.myButton.getLayoutParams() as CoordinatorLayout.LayoutParams).behavior = behavior
    }

    companion object {
        fun newInstance() = EpicEarthFragment()
    }

}