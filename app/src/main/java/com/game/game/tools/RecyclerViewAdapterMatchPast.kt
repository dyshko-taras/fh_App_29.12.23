package com.game.game.tools

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.game.game.R
import com.game.game.data.Match

class RecyclerViewAdapterMatchPast(var dataSet: List<Match>, var onClick: (Match) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapterMatchPast.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewLeagueNameMatchPast: TextView
        val textViewHomeTeamMatchPast: TextView
        val textViewAwayTeamMatchPast: TextView
        val textViewTeamHomeScoreMatchPast : TextView
        val textViewAwayTeamScoreMatchPast : TextView

        init {
            textViewLeagueNameMatchPast = view.findViewById(R.id.textViewLeagueNameMatchPast)
            textViewHomeTeamMatchPast = view.findViewById(R.id.textViewTeamHomeMatchPast)
            textViewAwayTeamMatchPast = view.findViewById(R.id.textViewAwayTeamMatchPast)
            textViewTeamHomeScoreMatchPast = view.findViewById(R.id.textViewTeamHomeScoreMatchPast)
            textViewAwayTeamScoreMatchPast = view.findViewById(R.id.textViewAwayTeamScoreMatchPast)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_match_past, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var match = dataSet[position]
        Log.d("TAG1", "${match.homeTeam}_${match.homeScore}_> ${match.awayTeam}_${match.awayScore}_")

        holder.textViewLeagueNameMatchPast.text = match.league
        holder.textViewHomeTeamMatchPast.text = match.homeTeam
        holder.textViewAwayTeamMatchPast.text = match.awayTeam
        holder.textViewTeamHomeScoreMatchPast.text = match.homeScore.toString()
        holder.textViewAwayTeamScoreMatchPast.text = match.awayScore.toString()

        val colorOrange = ContextCompat.getColor(holder.itemView.context, R.color.accent_primary_1)
        val colorBlack = ContextCompat.getColor(holder.itemView.context, R.color.text_body_text)

        if (match.homeScore > match.awayScore) {
            holder.textViewHomeTeamMatchPast.setTextColor(colorOrange)
            holder.textViewTeamHomeScoreMatchPast.setTextColor(colorOrange)
//            Log.d("TAG1", "COLOR ORANGE")
        } else if (match.homeScore < match.awayScore) {
//            holder.textViewAwayTeamMatchPast.setTextColor(colorOrange)
//            holder.textViewAwayTeamScoreMatchPast.setTextColor(colorOrange)
        } else {
//            holder.textViewHomeTeamMatchPast.setTextColor(colorBlack)
//            holder.textViewTeamHomeScoreMatchPast.setTextColor(colorBlack)
//            holder.textViewAwayTeamMatchPast.setTextColor(colorBlack)
//            holder.textViewAwayTeamScoreMatchPast.setTextColor(colorBlack)
        }
        holder.textViewHomeTeamMatchPast.setTextColor(colorBlack)
        holder.textViewTeamHomeScoreMatchPast.setTextColor(colorBlack)

        holder.itemView.setOnClickListener { onClick(match) }
    }
}