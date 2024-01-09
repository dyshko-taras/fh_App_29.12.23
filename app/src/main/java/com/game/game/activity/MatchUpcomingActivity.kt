package com.game.game.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.game.game.R
import com.game.game.data.Match
import com.game.game.tools.RecyclerViewAdapterMatchUpcoming
import com.game.game.viewmodel.MatchUpcomingViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MatchUpcomingActivity : AppCompatActivity() {

    private var TAG = "MatchUpcomingActivity1"

    private lateinit var buttonMatchPast: ImageView
    private lateinit var buttonMatchSetting: ImageView
    private lateinit var viewModel: MatchUpcomingViewModel
    private lateinit var listOfTextViewDays: List<androidx.appcompat.widget.AppCompatCheckedTextView>
    private lateinit var textViewDay0: androidx.appcompat.widget.AppCompatCheckedTextView
    private lateinit var textViewDay1: androidx.appcompat.widget.AppCompatCheckedTextView
    private lateinit var textViewDay2: androidx.appcompat.widget.AppCompatCheckedTextView
    private lateinit var textViewDay3: androidx.appcompat.widget.AppCompatCheckedTextView
    private lateinit var textViewDay4: androidx.appcompat.widget.AppCompatCheckedTextView
    private lateinit var textViewDay5: androidx.appcompat.widget.AppCompatCheckedTextView
    private lateinit var textViewDay6: androidx.appcompat.widget.AppCompatCheckedTextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var clickListener: (Match) -> Unit

    private val listOfDates = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_upcoming)
        initViews()
        setTextViewDays()
        setListeners()

        viewModel = ViewModelProvider(this).get(MatchUpcomingViewModel::class.java)

        viewModel.getData()
        textViewDay0.performClick()
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

        textViewDay0 = findViewById(R.id.textViewDay0)
        textViewDay1 = findViewById(R.id.textViewDay1)
        textViewDay2 = findViewById(R.id.textViewDay2)
        textViewDay3 = findViewById(R.id.textViewDay3)
        textViewDay4 = findViewById(R.id.textViewDay4)
        textViewDay5 = findViewById(R.id.textViewDay5)
        textViewDay6 = findViewById(R.id.textViewDay6)

        listOfTextViewDays = listOf(
            textViewDay0,
            textViewDay1,
            textViewDay2,
            textViewDay3,
            textViewDay4,
            textViewDay5,
            textViewDay6
        )

        recyclerView = findViewById(R.id.recyclerView)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListeners() {
        buttonMatchPast.setOnClickListener {
            MatchPastActivity.launch(this)
        }
        buttonMatchSetting.setOnClickListener {
            SettingsActivity.launch(this)
        }

        for (i in 0..6) {
            listOfTextViewDays[i].setOnClickListener {
                for (checkedTextView in listOfTextViewDays) {
                    checkedTextView.isChecked = false
                }
                val checkedTextView = it as CheckedTextView
                checkedTextView.isChecked = !checkedTextView.isChecked

                Log.d(TAG, "setListeners: $i")
                val dataString = listOfDates[i]
                viewModel.loadByDateAndElapsedTime0(dataString).observe(
                    this
                ) {
                    val recyclerViewAdapter = RecyclerViewAdapterMatchUpcoming(it, clickListener)
                    recyclerView.adapter = recyclerViewAdapter
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }

        clickListener = {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.card_dialog_info, null)

            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val notificationButton =
                view.findViewById<AppCompatButton>(R.id.buttonNotificationShareCard)
            notificationButton.setOnClickListener {
                ///////////////////////
                dialog.dismiss()
            }

            val cancelButton = view.findViewById<AppCompatButton>(R.id.buttonCancelWindowCard)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

            val textViewDateCard = view.findViewById<TextView>(R.id.textViewDateCard)
            textViewDateCard.text = it.date

            val textViewTimeCard = view.findViewById<TextView>(R.id.textViewTimeCard)
            textViewTimeCard.text = it.time

            val textViewLeagueNameCard = view.findViewById<TextView>(R.id.textViewLeagueNameCard)
            textViewLeagueNameCard.text = it.league

            val textViewHomeTeamCard = view.findViewById<TextView>(R.id.textViewHomeTeamCard)
            textViewHomeTeamCard.text = it.homeTeam

            val textViewAwayTeamCard = view.findViewById<TextView>(R.id.textViewAwayTeamCard)
            textViewAwayTeamCard.text = it.awayTeam
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun setTextViewDays() {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("d")
        val formatterAll = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        for (i in 0..6) {
            if (i != 0) {
                listOfTextViewDays[i].text = currentDate.plusDays(i.toLong()).format(formatter)
            }
            var date = currentDate.plusDays(i.toLong()).format(formatterAll).toString()
            Log.d(TAG, date)
            listOfDates.add(date)
        }
    }
}