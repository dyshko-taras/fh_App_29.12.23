package com.game.game.data

import androidx.lifecycle.LiveData
import java.util.concurrent.Flow

class MatchRepository(private val matchDao: MatchDao) {

    fun getAllMatches(): LiveData<List<Match>> {
        return matchDao.getAllMatches()
    }

    fun loadByDate(date: String): LiveData<List<Match>> {
        return matchDao.loadByDate(date)
    }

    fun loadByDateAndElapsedTime0(date: String): LiveData<List<Match>> {
        return matchDao.loadByDateAndElapsedTime0(date)
    }

    fun loadByDateAndElapsedTimeNot0(date: String): LiveData<List<Match>> {
        return matchDao.loadByDateAndElapsedTimeNot0(date)
    }

    suspend fun insertAll(vararg matches: Match) {
        matchDao.insertAll(*matches)
    }

    suspend fun delete(match: Match) {
        matchDao.delete(match)
    }
}