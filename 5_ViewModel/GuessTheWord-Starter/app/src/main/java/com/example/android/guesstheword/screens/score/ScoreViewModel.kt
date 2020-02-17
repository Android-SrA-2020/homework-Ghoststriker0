package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Martin Mallet on 2020-02-16
 */

class ScoreViewModel(finalScore: Int) : ViewModel() {
    // The final score
    var score = finalScore
    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }
}

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}