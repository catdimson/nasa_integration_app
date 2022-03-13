package ru.dkotik.nasaintegrationapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.ActivityMainBinding
import ru.dkotik.nasaintegrationapp.view.main.MarsPictureFragment
import ru.dkotik.nasaintegrationapp.view.main.PictureOfTheDayFragment

const val MainTheme = 1
const val SecondaryTheme = 2

class MainActivity : AppCompatActivity() {

    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(getRealStyle(getCurrentTheme()))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        initBottomNavigationView()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            MainTheme -> R.style.MainTheme
            SecondaryTheme -> R.style.SecondaryTheme
            else -> 0
        }
    }

    private fun initBottomNavigationView() {
        binding.menuBnvMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_view_main -> {
                    navigationTo(PictureOfTheDayFragment.newInstance())
                    true
                }
                R.id.bottom_view_marsohod -> {
                    navigationTo(MarsPictureFragment.newInstance())
                    true
                }
                R.id.bottom_view_epic_earth -> {
                    navigationTo(PictureOfTheDayFragment.newInstance())
                    true
                }
                R.id.bottom_view_asteroid -> {
                    navigationTo(PictureOfTheDayFragment.newInstance())
                    true
                }
                R.id.bottom_view_weather -> {
                    navigationTo(PictureOfTheDayFragment.newInstance())
                    true
                }
                else -> true
            }
        }
//        binding.menuBnvMain.selectedItemId = R.id.bottom_view_main

    }

    private fun navigationTo(f: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, f).commit()
    }
}