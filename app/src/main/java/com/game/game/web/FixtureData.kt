package com.game.game.web

import com.google.gson.annotations.SerializedName

class FixtureData {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("referee")
    var referee: String? = null

    @SerializedName("timezone")
    var timezone: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("timestamp")
    var timestamp: Int? = null

    @SerializedName("status")
    var status: StatusData? = null

    override fun toString(): String {
        return "Fixture(id=$id, referee=$referee, timezone=$timezone, date=$date, timestamp=$timestamp, status=$status)"
    }
}