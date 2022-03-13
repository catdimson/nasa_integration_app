package ru.dkotik.nasaintegrationapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentMainBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentPageMarsPhotoBinding
import ru.dkotik.nasaintegrationapp.utils.showSnackBarWithResources
import ru.dkotik.nasaintegrationapp.view.MainActivity
import ru.dkotik.nasaintegrationapp.viewmodel.AppState
import ru.dkotik.nasaintegrationapp.viewmodel.MarsPictureViewModel
import ru.dkotik.nasaintegrationapp.viewmodel.PictureOfTheDayViewModel

class MarsPictureFragment: Fragment() {

    private var _binding: FragmentPageMarsPhotoBinding? = null
    val binding: FragmentPageMarsPhotoBinding
        get () = _binding!!

    companion object {
        fun newInstance() = MarsPictureFragment()
    }

    private val viewModel: MarsPictureViewModel by lazy {
        ViewModelProvider(this).get(MarsPictureViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPageMarsPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessMars -> {
                if (data.serverResponseData.photos.isEmpty()){
                    Snackbar.make(binding.root, "В этот день curiosity не сделал ни одного снимка", Snackbar.LENGTH_SHORT).show()
                } else {
                    val url = data.serverResponseData.photos.first().imgSrc
                    binding.imageView.load(url)
                }
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.isVisible = true
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.isVisible = false
                binding.mainMarsPhoto.showSnackBarWithResources(
                    fragment = this,
                    text = R.string.error,
                    actionText = R.string.reload,
                    { viewModel.getMarsPicture() }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}