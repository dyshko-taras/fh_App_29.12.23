package com.game.game.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface MatchDao {
    @Query("SELECT * FROM match")
    suspend fun getAllMatches(): List<Match>

    @Query("SELECT * FROM match WHERE id IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<Match>

    @Insert
    suspend fun insertAll(vararg users: Match)

    @Delete
    suspend fun delete(user: Match)
}