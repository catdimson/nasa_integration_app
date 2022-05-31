package ru.dkotik.nasaintegrationapp.view.ux

import ru.dkotik.nasaintegrationapp.databinding.FragmentUxButtonBinding

class ButtonUXFragment : BaseUXFragment<FragmentUxButtonBinding>(FragmentUxButtonBinding::inflate) {

    companion object {
        fun newInstance() = ButtonUXFragment()
    }

}