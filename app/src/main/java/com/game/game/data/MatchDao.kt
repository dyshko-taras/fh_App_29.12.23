package com.game.game.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MatchDao {
    @Query("SELECT * FROM match")
    fun getAllMatches(): LiveData<List<Match>>

    //load matches by date
    @Query("SELECT * FROM match WHERE date = :date")
    fun loadByDate(date: String): LiveData<List<Match>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: Match)

    @Delete
    suspend fun delete(user: Match)

    //load matches by date and elapsed 0
    @Query("SELECT * FROM match WHERE date = :date AND elapsedTime = 0")
    fun loadByDateAndElapsedTime0(date: String): LiveData<List<Match>>


    //load matches by date and elapsed not 0
    @Query("SELECT * FROM match WHERE date = :date AND elapsedTime != 0")
    fun loadByDateAndElapsedTimeNot0(date: String): LiveData<List<Match>>
}