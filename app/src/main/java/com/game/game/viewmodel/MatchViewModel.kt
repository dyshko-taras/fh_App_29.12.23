package com.game.game.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.game.game.data.AppDatabase
import com.game.game.data.Match
import com.game.game.data.MatchRepository
import com.game.game.web.ApiFactory
import com.game.game.web.ResultJson
import kotlinx.coroutines.launch

class MatchViewModel(aplication: Application) : AndroidViewModel(aplication) {

    private val TAG = "MatchViewModel1"

    private var apiFactory = ApiFactory()

    private var _data = MutableLiveData<ResultJson>()
    val dataLiveData:LiveData<ResultJson>
        get() = _data

    private val matchDao = AppDatabase.getDatabase(aplication).matchDao()
    private val matchRepository = MatchRepository(matchDao)




//    fun getData() {
//        viewModelScope.launch {
//            try {
//                val response = apiFactory.apiService.get()
//                if (response.isSuccessful) {
//                    _data.value = response.body()
//                    Log.d(TAG, response.body().toString())
//                    Log.d(TAG, "Request successful. HTTP status code: ${response.code()}")
//                } else {
//                    Log.d(TAG, "Request not successful. HTTP status code: ${response.code()}")
//                }
//            } catch (e: Exception) {
//                Log.d(TAG, e.toString())
//            }
//        }
//    }

    fun getData() {
        viewModelScope.launch {
            try {
                val response = apiFactory.apiService.get("39", "2023", "2024-01-01", "2024-01-02")
                if (response.isSuccessful) {
                    _data.value = response.body()
                    Log.d(TAG, response.body().toString())
                    Log.d(TAG, "Request successful. HTTP status code: ${response.code()}")
                } else {
                    Log.d(TAG, "Request not successful. HTTP status code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }


}