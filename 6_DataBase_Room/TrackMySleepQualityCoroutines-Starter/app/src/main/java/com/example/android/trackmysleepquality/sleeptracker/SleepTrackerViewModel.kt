/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.formatNights
import kotlinx.coroutines.*

/**
 * ViewModel for SleepTrackerFragment.
 */
class SleepTrackerViewModel(
        val database: SleepDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    private val _navigateToSleepQuality = MutableLiveData<SleepNight>()

    val navigateToSleepQuality: LiveData<SleepNight>
        get() = _navigateToSleepQuality

    //This viewmodel allows me to cancel all coroutines started by this view model
    //when the view model is no longer used and is destroyed.
    private var viewModelJob = Job()

    //this here means that the coroutin will run on the main thread.
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val nights = database.getAllNights()

    val nightsString = Transformations.map(nights) { nights ->
        formatNights(nights, application.resources)
    }

    //This is to hold the current night
    private var tonight = MutableLiveData<SleepNight?>()


    //initialising block. Anything here is initialised
    init {
        initializeTonight()
    }

    //this will allows us to destroy all of the coroutines.
    //When the viewmodel is destroyed, it calls this method and clears all of the coroutines.
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onClear() {
        uiScope.launch {
            clear()
            tonight.value = null
        }
    }

    //function of the onClickHandler
    fun onStartTracking() {
        //adding the coroutine that captures the current time as start time
        uiScope.launch {
            val newNight = SleepNight()
            insert(newNight) //storing in the database
            tonight.value = getTonightFromDatabase() //storing the value as livedata
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = tonight.value ?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    //function to initialise tonight
    private fun initializeTonight() {
        //launching a coroutine that grabs the value on tonight
        uiScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }


    //Call a suspend function to do the long-running work, so that you don't block the UI thread while waiting for the result.
    //This enables us to do work asychronously
    private suspend fun getTonightFromDatabase(): SleepNight? {

        return withContext(Dispatchers.IO) {

            //it checks for a difference. If we get a different value, it gets updated.
            var night = database.getTonight()
            if (night?.endTimeMilli != night?.startTimeMilli) {
                night = null
            }
            night
        }
    }

    private suspend fun insert(night: SleepNight) {

        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

}



