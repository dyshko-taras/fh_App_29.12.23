package com.game.game.tools

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.game.game.R
import com.game.game.data.Match

class RecyclerViewAdapterMatchUpcoming(var dataSet: List<Match>, var onClick: (Match) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapterMatchUpcoming.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewLeagueNameMatchUpcoming: TextView
        val textViewHomeTeamMatchUpcoming: TextView
        val textViewAwayTeamMatchUpcoming: TextView
        val textViewTimeMatchUpcoming: TextView

        init {
            textViewLeagueNameMatchUpcoming =
                view.findViewById(R.id.textViewLeagueNameMatchPast)
            textViewHomeTeamMatchUpcoming =
                view.findViewById(R.id.textViewTeamHomeMatchPast)
            textViewAwayTeamMatchUpcoming = view.findViewById(R.id.textViewAwayTeamMatchPast)
            textViewTimeMatchUpcoming = view.findViewById(R.id.textViewTimeMatchUpcoming)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_match_upcoming, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewLeagueNameMatchUpcoming.text = dataSet[position].league
        holder.textViewHomeTeamMatchUpcoming.text = dataSet[position].homeTeam
        holder.textViewAwayTeamMatchUpcoming.text = dataSet[position].awayTeam
        holder.textViewTimeMatchUpcoming.text = dataSet[position].time

        holder.itemView.setOnClickListener { onClick(dataSet[position]) }
    }
}