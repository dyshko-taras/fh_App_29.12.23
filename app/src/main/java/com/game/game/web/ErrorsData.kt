package com.game.game.web

import com.google.gson.annotations.SerializedName

class ErrorsData {
    @SerializedName("time")
    val time: String? = null

    @SerializedName("bug")
    val bug: String? = null

    @SerializedName("report")
    val report: String? = null

    override fun toString(): String {
        return "Errors(time=$time, bug=$bug, report=$report)"
    }
}