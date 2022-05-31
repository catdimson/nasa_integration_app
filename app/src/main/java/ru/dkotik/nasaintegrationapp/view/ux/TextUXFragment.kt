package ru.dkotik.nasaintegrationapp.view.ux

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.provider.FontRequest
import androidx.core.provider.FontsContractCompat
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentUxTextBinding

class TextUXFragment : BaseUXFragment<FragmentUxTextBinding>(FragmentUxTextBinding::inflate) {

    companion object {
        fun newInstance() = TextUXFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = FontRequest("com.google.android.gms.fonts","com.google.android.gms",
            "Akronim", R.array.com_google_android_gms_fonts_certs)

        val callback= object : FontsContractCompat.FontRequestCallback(){
            override fun onTypefaceRetrieved(typeface: Typeface?) {
                binding.uxTextTitle.typeface = typeface
                binding.uxTextTitle2.typeface = typeface
                binding.uxTextTitle3.typeface = typeface
                super.onTypefaceRetrieved(typeface)
            }
        }
        FontsContractCompat.requestFont(requireContext(),request,callback, Handler(Looper.myLooper()!!))
    }

}