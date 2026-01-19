package com.doan.social.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.doan.social.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        installSplashScreen()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            insets
        }

        val onboardprefs = getSharedPreferences("onboarding_done", MODE_PRIVATE)
        Log.d("onboard", onboardprefs.getBoolean("onboarding_done",false).toString())
        if (!onboardprefs.getBoolean("onboarding_done",false)) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }
}