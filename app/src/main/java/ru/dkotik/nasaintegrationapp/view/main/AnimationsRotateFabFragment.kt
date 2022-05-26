package ru.dkotik.nasaintegrationapp.view.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.databinding.FragmentAnimationsRotateFabBinding

class AnimationsRotateFabFragment: Fragment() {

    private var _binding: FragmentAnimationsRotateFabBinding? = null
    private var isActiveAnimation = false
    private val DURATION_ANIMATION = 1000L

    val binding: FragmentAnimationsRotateFabBinding
        get () = _binding!!

    companion object {
        fun newInstance() = AnimationsRotateFabFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimationsRotateFabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHandlers()
    }

    private fun initHandlers() {
        binding.fab.setOnClickListener {
            isActiveAnimation = !isActiveAnimation
            if (isActiveAnimation) {
                runRotateAnimation(binding.plusImageview, 0f, 405f)
                runMoveAnimation(binding.optionOneContainer, -50f, -260f)
                runMoveAnimation(binding.optionTwoContainer, -20f, -130f)
                runChangeAlphaAnimation(binding.optionOneContainer, 1f, isActiveAnimation)
                runChangeAlphaAnimation(binding.optionTwoContainer, 1f, isActiveAnimation)
                binding.transparentBackground.animate().alpha(0.5f).duration = DURATION_ANIMATION
            } else {
                runRotateAnimation(binding.plusImageview, 405f, 0f)
                runMoveAnimation(binding.optionOneContainer, -260f, -50f)
                runMoveAnimation(binding.optionTwoContainer, -130f, -20f)
                runChangeAlphaAnimation(binding.optionOneContainer, 0f, isActiveAnimation)
                runChangeAlphaAnimation(binding.optionTwoContainer, 0f, isActiveAnimation)
                binding.transparentBackground.animate().alpha(0f).duration = DURATION_ANIMATION
            }

        }
    }

    private fun runRotateAnimation(view: View, start: Float, end: Float) {
        ObjectAnimator.ofFloat(view, View.ROTATION, start, end).setDuration(DURATION_ANIMATION).start()
    }

    private fun runMoveAnimation(view: View, start: Float, end: Float) {
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, start, end).setDuration(DURATION_ANIMATION).start()
    }

    private fun runChangeAlphaAnimation(view: View, alpha: Float, isClickable: Boolean) {
        view.animate()
            .alpha(alpha)
            .setDuration(DURATION_ANIMATION / 2)
            .setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    view.isClickable = isClickable
                }
            })
    }
}