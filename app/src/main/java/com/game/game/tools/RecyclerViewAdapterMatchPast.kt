package com.game.game.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.game.game.R
import com.game.game.data.Match

class RecyclerViewAdapterMatchPast(val dataSet: List<Match>, private val onClick: (Match) -> Unit) :
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
        holder.textViewLeagueNameMatchPast.text = dataSet[position].league
        holder.textViewHomeTeamMatchPast.text = dataSet[position].homeTeam
        holder.textViewAwayTeamMatchPast.text = dataSet[position].awayTeam
        holder.textViewTeamHomeScoreMatchPast.text = dataSet[position].homeScore.toString()
        holder.textViewAwayTeamScoreMatchPast.text = dataSet[position].awayScore.toString()

        val colorOrange = ContextCompat.getColor(holder.itemView.context, R.color.accent_primary_1)

        if (dataSet[position].homeScore > dataSet[position].awayScore) {
            holder.textViewHomeTeamMatchPast.setTextColor(colorOrange)
            holder.textViewTeamHomeScoreMatchPast.setTextColor(colorOrange)
        } else {
            holder.textViewAwayTeamMatchPast.setTextColor(colorOrange)
            holder.textViewAwayTeamScoreMatchPast.setTextColor(colorOrange)
        }
        holder.itemView.setOnClickListener { onClick(dataSet[position]) }
    }
}