package ru.dkotik.nasaintegrationapp.view.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentMainBinding
import ru.dkotik.nasaintegrationapp.databinding.FragmentMainStartBinding
import ru.dkotik.nasaintegrationapp.dto.pod.PODServerResponseData
import ru.dkotik.nasaintegrationapp.utils.showSnackBarWithResources
import ru.dkotik.nasaintegrationapp.view.MainActivity
import ru.dkotik.nasaintegrationapp.view.MainTheme
import ru.dkotik.nasaintegrationapp.view.SecondaryTheme
import ru.dkotik.nasaintegrationapp.view.chips.ChipsFragment
import ru.dkotik.nasaintegrationapp.viewmodel.AppState
import ru.dkotik.nasaintegrationapp.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment: Fragment(), View.OnClickListener {

    private var _binding: FragmentMainStartBinding? = null
    val binding: FragmentMainStartBinding
        get () = _binding!!
    private lateinit var parentActivity: MainActivity

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = activity as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.theme1.setOnClickListener(this)
        binding.theme2.setOnClickListener(this)

        viewModel.getData().observe(viewLifecycleOwner, {
            renderData(it)
        })
        viewModel.sendServerRequest()

        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bsl.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    /*BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
                    BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
                    BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
                    BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")*/
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("mylogs", "$slideOffset slideOffset")
            }
        })

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.yesterday -> {
                    viewModel.sendServerRequest(takeDate(-1))
                }
                R.id.today -> {
                    viewModel.sendServerRequest()
                }
            }
        }
    }

    private fun renderData(data: AppState) {
        when (data) {
            is AppState.SuccessPOD -> {
                val serverResponseData = data.serverResponseData
                val urlImage = serverResponseData.url

                if (serverResponseData.hdurl.isNullOrEmpty()) {
                    rebuildViewUnderVideo(serverResponseData)
                } else {
                    binding.imageView.load(urlImage)
                }

                binding.includedLoadingLayout.loadingLayout.isVisible = false
                binding.bsl.bottomSheetDescriptionHeader.text = serverResponseData.title
                binding.bsl.bottomSheetDescription.text = serverResponseData.explanation
                binding.imageView.load(urlImage)
            }
            is AppState.Loading -> {
                binding.includedLoadingLayout.loadingLayout.isVisible = true
            }
            is AppState.Error -> {
                binding.includedLoadingLayout.loadingLayout.isVisible = false
                binding.main.showSnackBarWithResources(
                    fragment = this,
                    text = R.string.error,
                    actionText = R.string.reload,
                    { viewModel.sendServerRequest() }
                )
            }
        }
    }

    private fun rebuildViewUnderVideo(data: PODServerResponseData) {
        binding.imageView.isVisible = false
        binding.groupWatchVideo.isVisible = true
        binding.btnLinkToVideo.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply { Uri.parse(data.url) })
        }
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.theme1 -> {
                parentActivity.setCurrentTheme(MainTheme)
                parentActivity.recreate() // применяем для всей активити и для всех дочерних фрагментов
            }
            R.id.theme2 -> {
                parentActivity.setCurrentTheme(SecondaryTheme)
                parentActivity.recreate() // применяем для всей активити и для всех дочерних фрагментов
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}