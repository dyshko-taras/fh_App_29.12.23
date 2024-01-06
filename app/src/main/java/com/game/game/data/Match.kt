package com.game.game.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "match")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val league: String,
    val date: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int
)
