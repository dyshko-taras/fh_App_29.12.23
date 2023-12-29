package com.game.game.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.game.game.R

class LoadActivity2 : AppCompatActivity() {

    private lateinit var button: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load2)
        initViews()
        setListeners()
    }

    //launch activity fun
    companion object{
        fun launch(activity: AppCompatActivity){
            val intent = Intent(activity, LoadActivity2::class.java)
            activity.startActivity(intent)
        }
    }

    private fun initViews() {
        button = findViewById(R.id.buttonNext)
    }

    private fun setListeners() {
        button.setOnClickListener {
            MatchUpcomingActivity.launch(this)
        }
    }
}