package com.game.game.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.game.game.R
import com.game.game.data.SharedPreferencesGame
import com.game.game.tools.AppUtils

class SettingsActivity : AppCompatActivity() {

    private lateinit var buttonEdit:ImageView
    private lateinit var buttonNotification:LinearLayout
    private lateinit var buttonPrivacyPolicy:LinearLayout
    private lateinit var buttonShareApp:LinearLayout
    private lateinit var buttonRateApp:LinearLayout
    private lateinit var buttonDeleteData:AppCompatButton
    private lateinit var buttonMatchUpcoming:ImageView
    private lateinit var buttonMatchPast:ImageView

    private lateinit var imageViewAvatar:ImageView
    private lateinit var textViewName:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initViews()
        setListeners()
        if (SharedPreferencesGame.getPathAvatar(this) != "") {
            Glide.with(this).load(SharedPreferencesGame.getPathAvatar(this)).circleCrop().into(imageViewAvatar)
        }
        textViewName.text = SharedPreferencesGame.getName(this)
    }

    //launch activity fun
    companion object{
        fun launch(activity: AppCompatActivity){
            val intent = Intent(activity, SettingsActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private fun initViews() {
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonNotification = findViewById(R.id.buttonNotification)
        buttonPrivacyPolicy = findViewById(R.id.buttonPrivacyPolicy)
        buttonShareApp = findViewById(R.id.buttonShareApp)
        buttonRateApp = findViewById(R.id.buttonRateApp)
        buttonDeleteData = findViewById(R.id.buttonDeleteData)
        buttonMatchUpcoming = findViewById(R.id.buttonMatchUpcoming)
        buttonMatchPast = findViewById(R.id.buttonMatchPast)
        imageViewAvatar = findViewById(R.id.imageViewAvatar)
        textViewName = findViewById(R.id.textViewName)
    }

    private fun setListeners() {
        buttonEdit.setOnClickListener {
            EditActivity.launch(this)
        }

        buttonNotification.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_notification, null)

            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val confirmButton = view.findViewById<TextView>(R.id.buttonBlock)
            confirmButton.setOnClickListener {
                /////
                dialog.dismiss()
            }

            val cancelButton = view.findViewById<TextView>(R.id.buttonAllow)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        buttonPrivacyPolicy.setOnClickListener {
            PrivacyPolicyActivity.launch(this)
        }

        buttonShareApp.setOnClickListener {
            AppUtils.shareApp(this)
        }

        buttonRateApp.setOnClickListener {
            AppUtils.rateApp(this)
        }

        buttonDeleteData.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_delete_data, null)

            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val confirmButton = view.findViewById<TextView>(R.id.buttonDelete)
            confirmButton.setOnClickListener {
                SharedPreferencesGame.setName(this, "Mykola")
                SharedPreferencesGame.setPathAvatar(this, "")
                launch(this)
                dialog.dismiss()
            }

            val cancelButton = view.findViewById<TextView>(R.id.buttonCancel)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


        buttonMatchUpcoming.setOnClickListener {
            MatchUpcomingActivity.launch(this)
        }
        buttonMatchPast.setOnClickListener {
            MatchPastActivity.launch(this)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}