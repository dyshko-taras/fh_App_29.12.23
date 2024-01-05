package com.game.game.web

import com.google.gson.annotations.SerializedName


class ResultJson {
    @SerializedName("get")
    val get: String? = null

//    @SerializedName("errors")
//    val errors: ErrorsData? = null

    @SerializedName("results")
    val results: Int? = null

    @SerializedName("response")
    val response: List<ResponseData>? = null

    override fun toString(): String {
        return "ResultJson(get=$get, results=$results, response=$response)"
    }
}