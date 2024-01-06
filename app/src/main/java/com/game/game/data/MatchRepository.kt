package com.game.game.data

import java.util.concurrent.Flow

class MatchRepository(private val matchDao: MatchDao) {

    suspend fun getAllMatches(): List<Match> {
        return matchDao.getAllMatches()
    }

    suspend fun loadAllByIds(userIds: IntArray): List<Match> {
        return matchDao.loadAllByIds(userIds)
    }

    suspend fun insertAll(vararg matches: Match) {
        matchDao.insertAll(*matches)
    }

    suspend fun delete(match: Match) {
        matchDao.delete(match)
    }
}