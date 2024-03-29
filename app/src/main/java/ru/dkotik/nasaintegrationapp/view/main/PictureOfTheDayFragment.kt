package ru.dkotik.nasaintegrationapp.view.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentMainStartBinding
import ru.dkotik.nasaintegrationapp.dto.pod.PODServerResponseData
import ru.dkotik.nasaintegrationapp.utils.showSnackBarWithResources
import ru.dkotik.nasaintegrationapp.view.MainActivity
import ru.dkotik.nasaintegrationapp.view.MainTheme
import ru.dkotik.nasaintegrationapp.view.SecondaryTheme
import ru.dkotik.nasaintegrationapp.viewmodel.AppState
import ru.dkotik.nasaintegrationapp.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment: Fragment(), View.OnClickListener {

    private var _binding: FragmentMainStartBinding? = null
    private var isScaleImage = false
    private val DURATION_ANIMATION = 1000L

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
                    runDisappearanceAnimation(binding.imageView)
                    viewModel.sendServerRequest(takeDate(-1))
                }
                R.id.today -> {
                    runDisappearanceAnimation(binding.imageView)
                    viewModel.sendServerRequest()
                }
            }
        }
    }

    private fun renderData(data: AppState) {
        resetAlphaToZero(binding.imageView)

        when (data) {
            is AppState.SuccessPOD -> {
                val serverResponseData = data.serverResponseData
                val urlImage = serverResponseData.url

                if (serverResponseData.hdurl.isNullOrEmpty()) {
                    rebuildViewUnderVideo(serverResponseData)
                } else {
                    binding.imageView.load(urlImage)
                    runAppearanceAnimation(binding.imageView)
                    isScaleImage = false
                    setImageScaleAnimation()
                }

                binding.bsl.bottomSheetDescriptionHeader.text = serverResponseData.title
                binding.bsl.bottomSheetDescription.text = serverResponseData.explanation
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
                binding.imageView.setImageResource(R.drawable.error_load)
                runAppearanceAnimation(binding.imageView)

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

    private fun setImageScaleAnimation() {
        binding.imageView.setOnClickListener {
            val changeBounds = ChangeImageTransform()
            changeBounds.duration = 1000
            TransitionManager.beginDelayedTransition(binding.root, changeBounds)
            isScaleImage = !isScaleImage
            binding.imageView.scaleType = if (isScaleImage) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.CENTER_INSIDE
        }
    }

    private fun resetAlphaToZero(view: View) {
        view.alpha = 0f
    }

    private fun runAppearanceAnimation(view: View) {
        view.animate()
            .alpha(1.0f)
            .setDuration(DURATION_ANIMATION)
    }

    private fun runDisappearanceAnimation(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(DURATION_ANIMATION)
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