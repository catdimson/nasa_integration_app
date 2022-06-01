package ru.dkotik.nasaintegrationapp.view.ux

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.dkotik.nasaintegrationapp.R
import ru.dkotik.nasaintegrationapp.view.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SecondaryTheme)
        setContentView(R.layout.activity_splash)

        findViewById<ImageView>(R.id.iv).animate().rotationBy(720f).setDuration(5000L).start()

        val cdt = object: CountDownTimer(2000,2000){
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, UXActivity::class.java))
                finish()
            }
        }
        cdt.start()

    }
}