package ru.dkotik.nasaintegrationapp.view.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.databinding.ActivityNavigationBinding

class NavigationActivity: AppCompatActivity() {
    
    lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
    
}