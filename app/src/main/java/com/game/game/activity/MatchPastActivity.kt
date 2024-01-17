package com.game.game.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.game.game.R
import com.game.game.data.Match
import com.game.game.tools.AppUtils
import com.game.game.tools.RecyclerViewAdapterMatchPast
import com.game.game.viewmodel.MatchPastViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MatchPastActivity : AppCompatActivity() {

    private var TAG = "MatchPastActivity1"

    private lateinit var buttonMatchUpcoming: ImageView
    private lateinit var buttonMatchSetting: ImageView
    private lateinit var viewModel: MatchPastViewModel
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
        setContentView(R.layout.activity_match_past)
        initViews()
        setTextViewDays()
        setListeners()

        viewModel = ViewModelProvider(this).get(MatchPastViewModel::class.java)

//        viewModel.getData()
        textViewDay0.performClick()
    }

    //launch activity fun
    companion object {
        fun launch(activity: AppCompatActivity) {
            val intent = Intent(activity, MatchPastActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private fun initViews() {
        buttonMatchUpcoming = findViewById(R.id.buttonMatchUpcoming)
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

    private fun setListeners() {
        buttonMatchUpcoming.setOnClickListener {
            MatchUpcomingActivity.launch(this)
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
                viewModel.loadByDateAndElapsedTimeNot0(dataString).observe(
                    this
                ) {
                    val recyclerViewAdapter = RecyclerViewAdapterMatchPast(it, clickListener)
                    recyclerView.adapter = recyclerViewAdapter
                    recyclerViewAdapter.notifyDataSetChanged()
                }
                //set text color for listOfTextViewDays
                for (checkedTextView in listOfTextViewDays) {
                    val textSecondaryColor = ContextCompat.getColor(this, R.color.text_secondary_text)
                    checkedTextView.setTextColor(textSecondaryColor)
                }
                //set text color
                if (checkedTextView.isChecked) {
                    val textBodyColor = ContextCompat.getColor(this, R.color.text_body_text)
                    checkedTextView.setTextColor(textBodyColor)
                }
            }
        }

        clickListener = {
            val match = it

            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.card_dialog_info, null)

            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val shareButton =
                view.findViewById<AppCompatButton>(R.id.buttonNotificationShareCard)
            shareButton.setOnClickListener {
                AppUtils.shareMatch(this, match)
                dialog.dismiss()
            }
            shareButton.text = "Share"

            val cancelButton = view.findViewById<AppCompatButton>(R.id.buttonCancelWindowCard)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

            val textViewDateCard = view.findViewById<TextView>(R.id.textViewDateCard)
            textViewDateCard.text = match.date

            val textViewTimeCard = view.findViewById<TextView>(R.id.textViewTimeCard)
            textViewTimeCard.text = match.time

            val textViewLeagueNameCard = view.findViewById<TextView>(R.id.textViewLeagueNameCard)
            textViewLeagueNameCard.text = match.league

            val textViewHomeTeamCard = view.findViewById<TextView>(R.id.textViewHomeTeamCard)
            textViewHomeTeamCard.text = match.homeTeam

            val textViewAwayTeamCard = view.findViewById<TextView>(R.id.textViewAwayTeamCard)
            textViewAwayTeamCard.text = match.awayTeam

            val textViewTeamHomeScore = view.findViewById<TextView>(R.id.textViewTeamHomeScore)
            textViewTeamHomeScore.text = match.homeScore.toString()

            val textViewTeamAwayScore = view.findViewById<TextView>(R.id.textViewTeamAwayScore)
            textViewTeamAwayScore.text = match.awayScore.toString()

            val colorOrange = ContextCompat.getColor(this, R.color.accent_primary_1)
            if (match.homeScore > match.awayScore) {
                textViewTeamHomeScore.setTextColor(colorOrange)
            } else {
                textViewTeamAwayScore.setTextColor(colorOrange)
            }
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
                listOfTextViewDays[i].text = currentDate.minusDays(i.toLong()).format(formatter)
            }
            var date = currentDate.minusDays(i.toLong()).format(formatterAll).toString()
            Log.d(TAG, date)
            listOfDates.add(date)
        }
    }
}