package com.game.game.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.game.game.R
import com.game.game.data.Match
import com.game.game.tools.AppUtils
import com.game.game.tools.RecyclerViewAdapterMatchPast
import com.game.game.viewmodel.MatchPastViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Calendar

class MatchPastActivity : AppCompatActivity() {

    private var TAG = "MatchPastActivity1"

    private lateinit var viewModel: MatchPastViewModel

    private lateinit var buttonMatchUpcoming: ImageView
    private lateinit var buttonMatchSetting: ImageView

    private lateinit var tabLayoutDays: TabLayout

    private lateinit var bottomNavigation: TableLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapterMatchPast
    private lateinit var clickListener: (Match) -> Unit

    private val listOfDates = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_past)
        initViews()
        setTextViewDays()
        setListeners()

        viewModel = ViewModelProvider(this).get(MatchPastViewModel::class.java)
        viewModel.isInternetConnection.observe(this) { isInternetConnection ->
            Log.d(TAG, "isInternetConnection: $isInternetConnection")
            if (!isInternetConnection) {
                showSnackbar("No internet connection", "Refresh", bottomNavigation, this) {
                    viewModel.checkInternetConnection(this)
                }
            } else {
                viewModel.getData()
            }
        }
        val currentTab = tabLayoutDays.getTabAt(tabLayoutDays.tabCount - 1)
        if (currentTab != null) {
            tabLayoutDays.selectTab(currentTab)
            selectTab(currentTab)
        }
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

        bottomNavigation = findViewById(R.id.bottomNavigation)

        tabLayoutDays = findViewById(R.id.tabLayoutDays)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerViewAdapter = RecyclerViewAdapterMatchPast(emptyList(), {})
        recyclerView.adapter = recyclerViewAdapter

        //fix recycler view animation
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun setListeners() {
        buttonMatchUpcoming.setOnClickListener {
            MatchUpcomingActivity.launch(this)
        }
        buttonMatchSetting.setOnClickListener {
            SettingsActivity.launch(this)
        }

        tabLayoutDays.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                selectTab(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        clickListener = { match ->

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
            shareButton.text = getString(R.string.share)

            val cancelButton = view.findViewById<AppCompatButton>(R.id.buttonCancelWindowCard)
            cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

            val textViewDateCard = view.findViewById<TextView>(R.id.textViewDateCard)

            //make data yyyy-MM-dd to dd.MM.yyyy
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = formatter.parse(match.date)
            formatter.applyPattern("dd.MM.yyyy")
            val formattedDate = formatter.format(date!!)

            textViewDateCard.text = formattedDate

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
            } else if (match.homeScore < match.awayScore) {
                textViewTeamAwayScore.setTextColor(colorOrange)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun selectTab(tab: TabLayout.Tab) {
        Log.d(TAG, "addOnTabSelectedListener: ${tab.position}")
        val dataString = listOfDates[tab.position]
        viewModel.loadByDateAndElapsedTimeNot0(dataString)
            .observe(this@MatchPastActivity) {
                recyclerViewAdapter.dataSet = it
                recyclerViewAdapter.onClick = clickListener
//                Log.d(TAG, "setListeners: $it")
                recyclerViewAdapter.notifyDataSetChanged()
            }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    @SuppressLint("SimpleDateFormat")
    private fun setTextViewDays() {
        val formatter = SimpleDateFormat("d")
        val formatterAll = SimpleDateFormat("yyyy-MM-dd")

        for (i in 6 downTo 0) {
            val tab = tabLayoutDays.getTabAt(Math.abs(i - 6))
            val date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_YEAR, -i)
            if (i != 0) {
                tab?.text = formatter.format(date.time)
            }
            val dateStr = formatterAll.format(date.time)
            Log.d(TAG, dateStr)
            listOfDates.add(dateStr)
        }
    }

    private fun showSnackbar(
        message: String,
        nameButton: String,
        view: View,
        context: Context,
        listener: View.OnClickListener
    ) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        val snackbarView = snackbar.view

        // Зміна фону Snackbar
        snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.accent_primary_1))

        snackbar.setAnchorView(view)

        // Зміна кольору та розміру тексту Snackbar
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextAppearance(R.style.roboto_regular_14_text_white_text)

        val textButton =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_action) as TextView
        textButton.setTextAppearance(R.style.roboto_semiBold_14_accent_secondary_2)

        snackbar.setAction(nameButton, listener)

        snackbar.show()
    }
}