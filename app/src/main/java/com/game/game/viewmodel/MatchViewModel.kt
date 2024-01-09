package com.game.game.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.game.game.data.AppDatabase
import com.game.game.data.Match
import com.game.game.data.MatchRepository
import com.game.game.web.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MatchViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "MatchViewModel1"
    private val matchDatabase = AppDatabase.getDatabase(application)
    private val matchRepository = MatchRepository(matchDatabase.matchDao())
    private var apiFactory = ApiFactory()

    private var _matchesLiveData = matchRepository.getAllMatches()
    val matchesLiveData: LiveData<List<Match>>
        get() = _matchesLiveData

    //
    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentDate = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val listDate = mutableListOf<String>()
                for (i in 0..6) {
                    listDate.add(currentDate.plusDays(i.toLong()).format(formatter))
//                    listDate.add(currentDate.minusDays(i.toLong()).format(formatter))
                }

                matchDatabase.clearAllTables()
                for (date in listDate) {
                    Log.d(TAG, date)
                    val responseJson = apiFactory.apiService.get(date)
                    if (responseJson.isSuccessful) {
                        Log.d(TAG, date + " " + responseJson.body()?.response?.size.toString())
                        responseJson.body()?.response?.forEach { responseData ->
                            val match = Match(
                                0,
                                responseData.league?.name ?: "null",
                                responseData.fixture?.date?.substring(0, 10) ?: "null",
                                responseData.fixture?.date?.substring(11, 16) ?: "null",
                                responseData.teams?.home?.name ?: "null",
                                responseData.teams?.away?.name ?: "null",
                                responseData.goalsData?.home ?: 0,
                                responseData.goalsData?.away ?: 0,
                                responseData.fixture?.status?.elapsed ?: 0
                            )
                            matchRepository.insertAll(match)
                        }
//                        Log.d(TAG, "Request successful. HTTP status code: ${responseJson.code()}")
                    } else {
                        Log.d(
                            TAG,
                            "Request not successful. HTTP status code: ${responseJson.code()}"
                        )
                    }
                }

            } catch (e: Exception) {
                Log.d(TAG, "----" + e.toString())
            }
        }
    }

    //load matches by date and elapsed 0
    fun loadByDateAndElapsedTime0(date: String): LiveData<List<Match>> {
        return matchRepository.loadByDateAndElapsedTime0(date)
    }
//

}