package com.game.game.tools

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.game.game.R
import com.game.game.data.Match
import java.text.SimpleDateFormat

class AppUtils {
    companion object {
        fun shareApp(context: Context) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this cool app: ${context.resources.getString(R.string.app_name)}. Download: https://play.google.com/store/apps/details?id=${
                    context.resources.getString(R.string.app_bundle)
                }"
            )
            context.startActivity(intent)
        }

        fun rateApp(context: Context) {
            val uri = Uri.parse("market://details?id=${context.resources.getString(R.string.app_bundle)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        @SuppressLint("SimpleDateFormat")
        fun shareMatch(context: Context, match: Match) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")

            //make data yyyy-MM-dd to dd.MM.yyyy
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val date = formatter.parse(match.date)
            formatter.applyPattern("dd.MM.yyyy")
            val formattedDate = formatter.format(date!!)

            val matchDetails = "Hi there! Look at match ${match.homeTeam} VS ${match.awayTeam} on $formattedDate at ${match.time}."
//            val matchDetails = "Match of ${match.league} between ${match.homeTeam} and ${match.awayTeam} took place on ${match.date} at ${match.time}. Final score: ${match.homeScore}-${match.awayScore}."
            intent.putExtra(Intent.EXTRA_TEXT, matchDetails)
            context.startActivity(intent)
        }
    }
}
