package com.game.game.web

import com.google.gson.annotations.SerializedName


class StatusData {
    @SerializedName("long")
    val long: String? = null

    @SerializedName("short")
    val short: String? = null

    @SerializedName("elapsed")
    val elapsed: Int? = null

    override fun toString(): String {
        return "Status(long=$long, short=$short, elapsed=$elapsed)"
    }
}