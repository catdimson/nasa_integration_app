package ru.dkotik.nasaintegrationapp.view.main

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentConstraintSetStartBinding

class ConstraintSetStartFragment: Fragment() {

    private var _binding: FragmentConstraintSetStartBinding? = null
    private var isActiveAnimation = false
    private val DURATION_ANIMATION = 1000L
    private val INTERPOLATOR_VALUE = 2.0f

    val binding: FragmentConstraintSetStartBinding
        get () = _binding!!

    companion object {
        fun newInstance() = ConstraintSetStartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConstraintSetStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHandlers()
        initFonts()
    }

    private fun initHandlers() {
        binding.backgroundImage.setOnClickListener {
            isActiveAnimation = !isActiveAnimation

            if (isActiveAnimation) {
                runAnimation(context, R.layout.fragment_constraint_set_end)
            } else {
                runAnimation(context, R.layout.fragment_constraint_set_start)
            }
        }
    }

    private fun runAnimation(context: Context?, layout: Int) {
        val changeBounds = ChangeBounds()
        val constraintSet = ConstraintSet()

        changeBounds.interpolator = AnticipateOvershootInterpolator(INTERPOLATOR_VALUE)
        changeBounds.duration = DURATION_ANIMATION
        TransitionManager.beginDelayedTransition(binding.constraintContainer, changeBounds)

        constraintSet.clone(context, layout)
        constraintSet.applyTo(binding.constraintContainer)
    }

    private fun initFonts() {
        val typefaceTheBomb = Typeface.createFromAsset(requireActivity().assets, "fonts/TheBomb.ttf")
        val typefaceSpaceQuest = Typeface.createFromAsset(requireActivity().assets, "fonts/SpaceQuest.ttf")
        binding.title.typeface = typefaceTheBomb
        binding.description.typeface = typefaceSpaceQuest
        binding.date.typeface = typefaceSpaceQuest
        binding.tap.typeface = typefaceTheBomb
    }
}