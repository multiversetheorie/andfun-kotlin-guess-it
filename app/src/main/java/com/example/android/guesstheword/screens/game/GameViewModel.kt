package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // The current word (internal)
    private val _word = MutableLiveData<String>()
    // The current word (external)
    val word: LiveData<String>
        get() = _word

    // The current score (internal)
    private val _score = MutableLiveData<Int>()
    // The current score (external)
    val score: LiveData<Int>
        // We override the original getter method, to refer to the _score
        get() = _score

    // Game finished event (internal)
    private val _isGameFinished = MutableLiveData<Boolean>()
    // Game finished event (external)
    val isGameFinished: LiveData<Boolean>
        get() = _isGameFinished


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        resetList()
        nextWord()
        _score.value = 0
        _word.value = ""
        _isGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed! :(")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _isGameFinished.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }

    /** Method for resetting the isGameFinished variable once we have shown the Toast / navigated away **/
    fun onGameFinishComplete() {
        _isGameFinished.value = false
    }




}