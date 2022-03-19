package ru.dkotik.nasaintegrationapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.view.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }
}