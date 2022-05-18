package ru.dkotik.nasaintegrationapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentRootBinding

class RootFragment: Fragment() {
    private var _binding: FragmentRootBinding? = null
    val binding: FragmentRootBinding
        get () = _binding!!

    companion object {
        fun newInstance() = RootFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRootBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigationView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initBottomNavigationView() {
        binding.menuBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_view_main -> {
                    navigationTo(PictureOfTheDayFragment.newInstance())
                    true
                }
                R.id.bottom_view_marsohod -> {
                    navigationTo(MarsPictureFragment.newInstance())
                    true
                }
                R.id.bottom_view_epic_earth -> {
                    navigationTo(EpicEarthFragment.newInstance())
                    true
                }
                R.id.bottom_view_asteroid -> {
                    navigationTo(PictureOfTheDayFragment.newInstance())
                    true
                }
                R.id.bottom_view_animations_rotate_fab -> {
                    navigationTo(AnimationsRotateFabFragment.newInstance())
                    true
                }
                else -> true
            }
        }
        binding.menuBnv.selectedItemId = R.id.bottom_view_main
    }

    private fun navigationTo(f: Fragment) {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_common_slide_in,
                R.anim.fragment_common_fade_out,
                R.anim.fragment_common_slide_out,
                R.anim.fragment_common_fade_in
            )
            .replace(R.id.container, f)
            .addToBackStack(null)
            .commit()
    }
}