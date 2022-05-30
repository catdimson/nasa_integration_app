package ru.dkotik.nasaintegrationapp.view.main

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.FragmentSpansBinding
import java.util.*

class SpansFragment: Fragment() {

    private var _binding: FragmentSpansBinding? = null

    val binding: FragmentSpansBinding
        get () = _binding!!

    companion object {
        fun newInstance() = SpansFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpansBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spannableStringBuilder = initChannel()
        initSpannableEdition(spannableStringBuilder)
        initMulticolorSpannableText()
    }

    private fun initChannel(): SpannableStringBuilder {
        val tvSpan = binding.tvSpan
        val spannableStringBuilder = SpannableStringBuilder(tvSpan.text)
        tvSpan.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
        return tvSpan.text as SpannableStringBuilder
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initSpannableEdition(spannableStringBuilder: SpannableStringBuilder) {
        changeColorText(spannableStringBuilder)
        changeTypeface(spannableStringBuilder)
        changeSize(spannableStringBuilder)
        changeScaleX(spannableStringBuilder)
        changeBackgroundColor(spannableStringBuilder)
        changeForegroundColor(spannableStringBuilder)
        changeBoldA(spannableStringBuilder)
        changeUnderlineV(spannableStringBuilder)
        changeTextOnQuote(spannableStringBuilder)
    }

    /** 1. Изменить цвет текста */
    private fun changeColorText(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder
            .setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorAccent)),
                0,
                spannableStringBuilder.length/2,
                SpannableString.SPAN_INCLUSIVE_INCLUSIVE
            )

        spannableStringBuilder
            .setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(),R.color.teal_200)),
                spannableStringBuilder.length/2,
                spannableStringBuilder.length,
                SpannableString.SPAN_INCLUSIVE_INCLUSIVE
            )
    }

    /** 2. Изменить тип шрифта */
    @RequiresApi(Build.VERSION_CODES.P)
    private fun changeTypeface(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.setSpan(
            TypefaceSpan(
                Typeface.createFromAsset(requireActivity().assets, "fonts/SpaceQuest.ttf")),
            0,
            spannableStringBuilder.length/2,
            SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    }

    /** 3. Изменить размер текста */
    private fun changeSize(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.setSpan(
            RelativeSizeSpan(2.0f), 0, 37, 0
        )
    }

    /** 4. Расширить текст по горизонтали в 2 раза */
    private fun changeScaleX(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.setSpan(
            ScaleXSpan(2.0f), 168, 225, 0
        )
    }

    /** 5. Изменить цвет фона текста */
    @SuppressLint("ResourceAsColor")
    private fun changeBackgroundColor(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.setSpan(
            BackgroundColorSpan(R.color.design_default_color_primary), 168, 225, 0
        )
    }

    /** 6. Изменить цвет самого текста */
    @SuppressLint("ResourceAsColor")
    private fun changeForegroundColor(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(R.color.white), 168, 225, 0
        )
    }

    /** 7. Сделать все буквы А - bold */
    private fun changeBoldA(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.indices.forEach {
            if (spannableStringBuilder[it]== 'a') {
                spannableStringBuilder.setSpan(
                    StyleSpan(Typeface.BOLD), it, it + 1, 0
                )
            }
        }
    }

    /** 8. Подчеркнуть все буквы V */
    private fun changeUnderlineV(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.indices.forEach {
            if (spannableStringBuilder[it]== 'v') {
                spannableStringBuilder.setSpan(
                    UnderlineSpan(), it, it + 1, 0
                )
            }
        }
    }

    /** 9. Выделить абзац, как цитату */
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ResourceAsColor")
    private fun changeTextOnQuote(spannableStringBuilder: SpannableStringBuilder) {
        spannableStringBuilder.setSpan(
            QuoteSpan(R.color.red2_300, 10, 20), 0, 226, 0
        )
    }

    /** Текст переливается цветами радуги */
    @Synchronized
    private fun initMulticolorSpannableText() {

        Thread {

            val tvSpan = binding.tvMulticolorSpan
            val spannableStringBuilder = SpannableStringBuilder(tvSpan.text)
            tvSpan.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
            val ssb = tvSpan.text as SpannableStringBuilder

            val multicolors = LinkedList(listOf(
                R.color.mul_red,
                R.color.mul_orange,
                R.color.mul_yellow,
                R.color.mul_green,
                R.color.mul_blue,
                R.color.mul_indigo,
                R.color.mul_violet
            ))

            while (true) {

                requireActivity().runOnUiThread {
                    val ost = ssb.length % multicolors.size
                    var index = 0
                    val sizeColors = multicolors.size

                    fun getIndex(): Int {
                        return index
                    }

                    fun getNextIndex(): Int {
                        index++
                        return index
                    }

                    fun resetIndex() {
                        index = 0
                    }

                    if (ost == 0) {
                        val step = ssb.length / multicolors.size
                        for (i in 0 until sizeColors) {
                            ssb.setSpan(
                                ForegroundColorSpan(ContextCompat.getColor(this@SpansFragment.requireContext(), multicolors[getIndex()])),
                                step * getIndex(),
                                step * getNextIndex(),
                                0
                            )
                        }
                    } else {
                        val step = ssb.length / (multicolors.size - 1)
                        val newOst = ssb.length % (multicolors.size - 1)

                        for (i in 0 until sizeColors - 1) {
                            ssb.setSpan(
                                ForegroundColorSpan(ContextCompat.getColor(this@SpansFragment.requireContext(), multicolors[getIndex()])),
                                step * getIndex(),
                                step * getNextIndex(),
                                0
                            )
                        }

                        ssb.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(this@SpansFragment.requireContext(), multicolors[getIndex()])),
                            step * getIndex(),
                            step * getIndex() + newOst,
                            0
                        )

                        multicolors.add(multicolors[0])
                        multicolors.remove()
                    }

                    resetIndex()
                }

                Thread.sleep(200)
            }
        }.start()
    }
}