package com.game.game.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.game.game.R

class MatchUpcomingActivity : AppCompatActivity() {

    private lateinit var buttonMatchPast: ImageView
    private lateinit var buttonMatchSetting: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_upcoming)
        initViews()
        setListeners()
    }

    //launch activity fun
    companion object{
        fun launch(activity: AppCompatActivity){
            val intent = Intent(activity, MatchUpcomingActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private fun initViews() {
        buttonMatchPast = findViewById(R.id.buttonMatchPast)
        buttonMatchSetting = findViewById(R.id.buttonMatchSetting)
    }

    private fun setListeners() {
        buttonMatchPast.setOnClickListener {
            MatchPastActivity.launch(this)
        }
        buttonMatchSetting.setOnClickListener {
            SettingsActivity.launch(this)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}