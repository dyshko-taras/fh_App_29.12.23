package com.game.game.web

import com.google.gson.annotations.SerializedName

class LeagueData {
    @SerializedName("id")
    val id: Int? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("country")
    val country: String? = null

    @SerializedName("logo")
    val logo: String? = null

    @SerializedName("flag")
    val flag: String? = null

    @SerializedName("season")
    val season: Int? = null

    @SerializedName("round")
    val round: String? = null

    override fun toString(): String {
        return "League(id=$id, name=$name, country=$country, logo=$logo, flag=$flag, season=$season, round=$round)"
    }
}