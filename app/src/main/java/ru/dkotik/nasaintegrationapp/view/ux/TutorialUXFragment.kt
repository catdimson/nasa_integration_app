package ru.dkotik.nasaintegrationapp.view.ux

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.databinding.FragmentUxTutorialBinding
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener

class TutorialUXFragment: Fragment() {

    private val KEY_SP = "sp_ux"
    private val KEY_NOT_READ_TUTORIAL = "is_not_read_tutorial"

    private var _binding: FragmentUxTutorialBinding? = null
    private val binding: FragmentUxTutorialBinding
        get() = _binding!!

    companion object{
        fun newInstance() = TutorialUXFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUxTutorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (getReadTutorialFlag()) {
            showTutorial()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setReadTutorialFlag() {
        val sharedPreferences = requireActivity().getSharedPreferences(KEY_SP, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NOT_READ_TUTORIAL, false)
        editor.apply()
    }

    private fun getReadTutorialFlag(): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences(KEY_SP, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_NOT_READ_TUTORIAL, true)
    }

    private fun showTutorial() {
        val builder = GuideView.Builder(requireContext())
            .setTitle("Устаревший подход")
            .setContentText("Мы не используем работу с цветом, размером и шрифтами")
            .setGravity(Gravity.center)
            .setDismissType(DismissType.anywhere)
            .setTargetView(binding.btnBad)
            .setDismissType(DismissType.anywhere)
            .setGuideListener {
                val builder2 = GuideView.Builder(requireContext())
                    .setTitle("Новый(material) подход")
                    .setContentText("Мы используем работу ТОЛЬКО с прозрачностями и пространством")
                    .setGravity(Gravity.center)
                    .setDismissType(DismissType.anywhere)
                    .setTargetView(binding.btnGood)
                    .setDismissType(DismissType.anywhere)
                    .setGuideListener { setReadTutorialFlag() }
                builder2.build().show()
            }
        builder.build().show()
    }
}