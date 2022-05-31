package ru.dkotik.nasaintegrationapp.view.ux

import ru.dkotik.nasaintegrationapp.databinding.FragmentUxTextBinding

class TextUXFragment : BaseUXFragment<FragmentUxTextBinding>(FragmentUxTextBinding::inflate) {

    companion object {
        fun newInstance() = TextUXFragment()
    }

}