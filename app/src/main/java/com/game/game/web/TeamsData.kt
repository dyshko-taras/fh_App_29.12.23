package com.game.game.web

import com.google.gson.annotations.SerializedName

class TeamsData {
    @SerializedName("home")
    val home: TeamData? = null

    @SerializedName("away")
    val away: TeamData? = null

    override fun toString(): String {
        return "Teams(home=$home, away=$away)"
    }
}