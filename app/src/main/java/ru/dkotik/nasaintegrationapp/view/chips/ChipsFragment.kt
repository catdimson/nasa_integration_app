package ru.dkotik.nasaintegrationapp.view.chips

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentChipsBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentMainBinding
import ru.dkotik.nasaintegrationapp.view.MainActivity
import ru.dkotik.nasaintegrationapp.viewmodel.PictureOfTheDayViewModel

class ChipsFragment: Fragment() {
    private var _binding: FragmentChipsBinding? = null
    val binding: FragmentChipsBinding
        get () = _binding!!
    var isMain: Boolean = true

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}