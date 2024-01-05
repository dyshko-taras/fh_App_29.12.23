package com.game.game.web

import com.google.gson.annotations.SerializedName

class GoalsData {
    @SerializedName("home")
    val home: Int? = null

    @SerializedName("away")
    val away: Int? = null

    override fun toString(): String {
        return "Goals(home=$home, away=$away)"
    }
}