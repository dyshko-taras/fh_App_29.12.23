package com.game.game.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.game.game.R

class MatchPastActivity : AppCompatActivity() {

    private lateinit var buttonMatchUpcoming: ImageView
    private lateinit var buttonMatchSetting: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_past)
        initViews()
        setListeners()
    }

    //launch activity fun
    companion object{
        fun launch(activity: AppCompatActivity){
            val intent = Intent(activity, MatchPastActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private fun initViews() {
        buttonMatchUpcoming = findViewById(R.id.buttonMatchUpcoming)
        buttonMatchSetting = findViewById(R.id.buttonMatchSetting)
    }

    private fun setListeners() {
        buttonMatchUpcoming.setOnClickListener {
            MatchUpcomingActivity.launch(this)
        }
        buttonMatchSetting.setOnClickListener {
            SettingsActivity.launch(this)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}