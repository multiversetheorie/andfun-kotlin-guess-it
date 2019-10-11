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

package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )

        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        // Setting up the custom factory for our viewModel and getting the viewModel from ViewModelProviders
        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)

        // Pass the viewModel into binding ("Letting binding know about the viewModel")
        binding.scoreViewModel = viewModel

        // Setting up method to execute when the finalScore variable in the ViewModel changes
        val finalScoreObserver = Observer<Int> { newFinalScore ->
            binding.scoreText.text = viewModel.score.value.toString()
        }

        // Set OnClickListener on the Play Again button, so that clicking it calls the onPlayAgain method in the ViewModel
        // binding.playAgainButton.setOnClickListener { viewModel.onPlayAgain() }

        // Setting up method to execute when the eventPlayAgain variable in the ViewModel changes
        val eventPlayAgainObserver = Observer<Boolean> {playAgainClicked ->
            // If playAgainClicked...
            if (playAgainClicked) {
                // ...then navigate to the GameFragment...
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                // ...and reset the eventPlayAgain variable to false.
                viewModel.onPlayAgainComplete()
            }
        }

        // Set up observers to observe score and eventPlayAgain
        viewModel.score.observe(this, finalScoreObserver)
        viewModel.eventPlayAgain.observe(this, eventPlayAgainObserver)

        return binding.root
    }

}
