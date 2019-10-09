/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.MainActivity
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        // Setting up methods to execute when the score or word changes
        val scoreObserver = Observer<Int> { newScore ->
            binding.scoreText.text = viewModel.score.value.toString()
        }

        val wordObserver = Observer<String> { newWord ->
            binding.wordText.text = viewModel.word.value
        }

        // Setting up methods to execute when the game is finished
        val gameFinishedObserver = Observer<Boolean> { hasFinished ->
            if (hasFinished) {
                gameFinished()
                // Reset the isGameFinished variable
                viewModel.onGameFinishComplete()
            }
        }

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        // Setting up observers to observe score and word
        viewModel.score.observe(this, scoreObserver)
        viewModel.word.observe(this, wordObserver)

        // Setting up observers to observe if the game is finished
        viewModel.isGameFinished.observe(this, gameFinishedObserver)

        return binding.root


    }

    /**
     * Called when the game is finished
     */
    fun gameFinished() {
        // If viewModel.score value is an Integer, pass that Integer, if it is null, pass 0
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
//        Toast.makeText(this.activity, "This game is now finished and you either suck or rule...", Toast.LENGTH_LONG).show()
    }
}
