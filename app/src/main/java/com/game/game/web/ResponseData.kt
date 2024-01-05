package com.game.game.web

import com.google.gson.annotations.SerializedName

class ResponseData {
    @SerializedName("fixture")
    var fixture: FixtureData? = null

    @SerializedName("league")
    var league: LeagueData? = null

    @SerializedName("teams")
    var teams: TeamsData? = null

    @SerializedName("goals")
    var goalsData: GoalsData? = null

    override fun toString(): String {
        return "ResponseData(fixture=$fixture, league=$league, teams=$teams, goalsData=$goalsData)"
    }
}