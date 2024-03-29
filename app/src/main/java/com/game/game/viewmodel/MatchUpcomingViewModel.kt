package com.game.game.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.game.game.data.AppDatabase
import com.game.game.data.Match
import com.game.game.data.MatchRepository
import com.game.game.web.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class MatchUpcomingViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "MatchViewModel1"
    private val matchDatabase = AppDatabase.getDatabase(application)
    private val matchRepository = MatchRepository(matchDatabase.matchDao())
    private var apiFactory = ApiFactory(application)
    private val listDate = mutableListOf<String>()

    private val isInternetConnectionLD: MutableLiveData<Boolean> = MutableLiveData()

    val isInternetConnection: LiveData<Boolean>
        get() = isInternetConnectionLD


    init {
        checkInternetConnection(application)
    }

    fun getData(currentDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, currentDate)
                val responseJson = apiFactory.apiService.get(currentDate)
                if (responseJson.isSuccessful) {
                    Log.d(TAG, currentDate + " " + responseJson.body()?.response?.size.toString())
                    val listOfMatches = mutableListOf<Match>()
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
                        listOfMatches.add(match)
                    }
                    Log.d(TAG, "========: ${listOfMatches.isEmpty()}")
                    matchRepository.insertAll(*listOfMatches.toTypedArray())
                } else {
                    Log.d(
                        TAG,
                        "Request not successful. HTTP status code: ${responseJson.code()}"
                    )
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

    @SuppressLint("SimpleDateFormat")
    private fun setListOfDates() {
        val formatterAll = SimpleDateFormat("yyyy-MM-dd")

        for (i in 0..6) {
            val date = Calendar.getInstance()
            date.add(Calendar.DAY_OF_YEAR, i)
//            date.add(Calendar.DAY_OF_YEAR, -i)
            listDate.add(formatterAll.format(date.time))
        }
    }


    fun checkInternetConnection(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            val activeNetworkInfo = it.activeNetworkInfo
            isInternetConnectionLD.postValue(activeNetworkInfo?.isConnected ?: false)
        } ?: run {
            isInternetConnectionLD.postValue(false)
        }
    }
}