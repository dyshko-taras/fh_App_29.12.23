package com.game.game.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.game.game.R
import com.game.game.data.Match
import com.game.game.tools.RecyclerViewAdapterMatchUpcoming
import com.game.game.viewmodel.MatchUpcomingViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.util.Calendar


class MatchUpcomingActivity : AppCompatActivity() {

    private var TAG = "MatchUpcomingActivity1"

    private lateinit var viewModel: MatchUpcomingViewModel

    private lateinit var buttonMatchPast: ImageView
    private lateinit var buttonMatchSetting: ImageView

    private lateinit var tabLayoutDays: TabLayout

    private lateinit var bottomNavigation: TableLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapterMatchUpcoming
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
        viewModel.checkInternetConnection(this)
        viewModel.isInternetConnection.observe(this) { isInternetConnection ->
            Log.d(TAG, "isInternetConnection: $isInternetConnection")
            if (!isInternetConnection) {
                showSnackar("No internet connection", "Refresh", bottomNavigation, this) {
                    viewModel.checkInternetConnection(this)
                }
            } else {
                viewModel.getData()
            }
        }

        val currentTab = tabLayoutDays.getTabAt(tabLayoutDays.selectedTabPosition)
        if (currentTab != null) {
            selectTab(currentTab)
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

        bottomNavigation = findViewById(R.id.bottomNavigation)

        tabLayoutDays = findViewById(R.id.tabLayoutDays)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerViewAdapter = RecyclerViewAdapterMatchUpcoming(emptyList(), {})
        recyclerView.adapter = recyclerViewAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListeners() {
        buttonMatchPast.setOnClickListener {
            MatchPastActivity.launch(this)
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

            val textViewLeagueNameCard =
                view.findViewById<TextView>(R.id.textViewLeagueNameCard)
            textViewLeagueNameCard.text = it.league

            val textViewHomeTeamCard = view.findViewById<TextView>(R.id.textViewHomeTeamCard)
            textViewHomeTeamCard.text = it.homeTeam

            val textViewAwayTeamCard = view.findViewById<TextView>(R.id.textViewAwayTeamCard)
            textViewAwayTeamCard.text = it.awayTeam
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun selectTab(tab: TabLayout.Tab) {
        Log.d(TAG, "addOnTabSelectedListener: ${tab.position}")
        val dataString = listOfDates[tab.position]
        viewModel.loadByDateAndElapsedTime0(dataString)
            .observe(this@MatchUpcomingActivity) {
                recyclerViewAdapter.dataSet = it
                recyclerViewAdapter.onClick = clickListener
                Log.d(TAG, "setListeners: $it")
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

        for (i in 0 until tabLayoutDays.tabCount) {
            val tab = tabLayoutDays.getTabAt(i)
            val date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_YEAR, i)
            if (i != 0) {
                tab?.text = formatter.format(date.time)
            }
            val dateStr = formatterAll.format(date.time)
            Log.d(TAG, dateStr)
            listOfDates.add(dateStr)
        }
    }

    private fun showSnackar(
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