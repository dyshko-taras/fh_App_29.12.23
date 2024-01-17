package com.game.game.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.game.game.R
import com.game.game.data.SharedPreferencesGame
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadActivity : AppCompatActivity() {

    private lateinit var lottieAnimationView: com.airbnb.lottie.LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        initViews()
//        if (SharedPreferencesGame.isFirstLaunch(this)) {
//            GlobalScope.launch {
//                delay(1000)
//                PrivacyPolicyActivity.launch(this@LoadActivity)
//            }
//
//        } else {
//            GlobalScope.launch {
//                delay(1000)
//                MatchUpcomingActivity.launch(this@LoadActivity)
//            }
//        }
        lottieAnimationView.addAnimatorUpdateListener {
            if (it.animatedFraction == 1f) {
                if (SharedPreferencesGame.isFirstLaunch(this)) {
                    PrivacyPolicyActivity.launch(this)
                } else {
                    MatchUpcomingActivity.launch(this)
                }
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun initViews() {
        lottieAnimationView = findViewById(R.id.lottieAnimationView)
    }
}