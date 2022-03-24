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
import ru.dkotik.nasaintegrationapp.databinding.FragmentPageMarsPhotoBinding
import ru.dkotik.nasaintegrationapp.dto.marsphotos.MarsServerResponseData
import ru.dkotik.nasaintegrationapp.utils.showSnackBarWithResources
import ru.dkotik.nasaintegrationapp.viewmodel.AppState
import ru.dkotik.nasaintegrationapp.viewmodel.MarsPictureViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        viewModel.getMarsPicture()
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessMars -> {
                if (data.serverResponseData.photos.isEmpty()){
                    Snackbar.make(binding.root, "В этот день curiosity не сделал ни одного снимка", Snackbar.LENGTH_SHORT).show()
                } else {
                    val responseData = data.serverResponseData.photos.first()
                    fullFields(responseData)
                    binding.includedLoadingLayout.loadingLayout.isVisible = false
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

    private fun fullFields(data: MarsServerResponseData) {
        binding.cameraName.text = data.camera?.name ?: ""
        binding.cameraFullName.text = data.camera?.fullName ?: ""

        binding.roverName.text = data.rover?.name ?: ""
        binding.roverLandingDate.text = data.rover?.landingDate ?: ""
        binding.roverStatus.text = data.rover?.status ?: ""

        binding.imageView.load(data.imgSrc)
        binding.photoMarsDate.text = data.earthDate
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}