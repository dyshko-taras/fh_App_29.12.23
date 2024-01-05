package com.game.game.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.game.game.R
import com.game.game.viewmodel.MatchViewModel

class MatchUpcomingActivity : AppCompatActivity() {

    private lateinit var buttonMatchPast: ImageView
    private lateinit var buttonMatchSetting: ImageView
    private lateinit var matchViewModel: MatchViewModel
    private var TAG = "MatchUpcomingActivity1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_upcoming)
        initViews()
        setListeners()

        Log.d(TAG, "onCreate: ")

        matchViewModel = ViewModelProvider(this).get(MatchViewModel::class.java)
        matchViewModel.getData()
        Log.d(TAG, "get data: ")

        matchViewModel.dataLiveData.observe(this) {
            Log.d(TAG, "data: ")
            Log.d(TAG, it.toString())
        }
    }

    //launch activity fun
    companion object {
        fun launch(activity: AppCompatActivity) {
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
//            SettingsActivity.launch(this)
            Log.d(TAG, "setListeners: ${matchViewModel.dataLiveData.value}")
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}