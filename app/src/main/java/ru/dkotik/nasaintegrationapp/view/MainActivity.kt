package ru.dkotik.nasaintegrationapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.view.main.PictureOfTheDayFragment

const val MainTheme = 1
const val SecondaryTheme = 2

class MainActivity : AppCompatActivity() {

    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(getRealStyle(getCurrentTheme()))

        setContentView(R.layout.activity_main)

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
}