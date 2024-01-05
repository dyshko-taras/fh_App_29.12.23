package com.game.game.web

import com.google.gson.annotations.SerializedName

class TeamData {
    @SerializedName("id")
    val id: Int? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("logo")
    val logo: String? = null

    @SerializedName("winner")
    val winner: Boolean? = null

    override fun toString(): String {
        return "Team(id=$id, name=$name, logo=$logo, winner=$winner)"
    }
}