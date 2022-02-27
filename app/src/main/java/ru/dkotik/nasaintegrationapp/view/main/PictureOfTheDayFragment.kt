package ru.dkotik.nasaintegrationapp.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import ru.dkotik.nasaintegrationapp.databinding.FragmentMainBinding
import ru.dkotik.nasaintegrationapp.viewmodel.PictureOfTheDayDataState
import ru.dkotik.nasaintegrationapp.viewmodel.PictureOfTheDayViewModel

class PictureOfTheDayFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding: FragmentMainBinding
        get () = _binding!!

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        viewModel.sendServerRequest()
    }

    private fun renderData(data: PictureOfTheDayDataState) {
        when (data) {
            is PictureOfTheDayDataState.Success -> {
                val serverResponseData = data.serverResponseData
                val urlImage = serverResponseData.url

                if (urlImage.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    //Отобразите фото
                    //showSuccess()
                    //Coil в работе: достаточно вызвать у нашего ImageView
                    //нужную extension-функцию и передать ссылку и заглушки для placeholder
                    binding.imageView.load(urlImage)
                }
            }
            is PictureOfTheDayDataState.Loading -> {
                //Отобразите загрузку
                //showLoading()
            }
            is PictureOfTheDayDataState.Error -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}