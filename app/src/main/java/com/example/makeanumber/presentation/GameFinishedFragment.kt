package com.example.makeanumber.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.makeanumber.R
import com.example.makeanumber.databinding.FragmentGameFinishedBinding


class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()
    private val gameResult by lazy { args.result }
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            retry()
        }
        setResultDisplay()
    }

    private fun setResultDisplay() {
        if (gameResult.win) {
            binding.emojiResult.setImageResource(R.drawable.happy_robot)
            binding.tvResult.text = getString(R.string.game_result_win)
            binding.tvResult.setTextColor(chooseColor(true))
        } else {
            binding.emojiResult.setImageResource(R.drawable.sad_robot)
            binding.tvResult.text = getString(R.string.game_result_lose)
            binding.tvResult.setTextColor(chooseColor(false))
        }
        setTexts()
    }

    private fun setTexts() {
        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.required_score),
            gameResult.gameSettings.minCountOfRightAnswers
        )
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers
        )
        binding.tvScoreAnswers.text =
            String.format((getString(R.string.score_answers)), gameResult.countOfRightAnswers)
        val percentage =
            (gameResult.countOfRightAnswers / gameResult.countofQuestionsAnswered.toDouble() * 100).toInt()
        binding.tvScorePercentage.text =
            String.format(getString(R.string.score_percentage), percentage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retry() {
        findNavController().popBackStack()
    }

    private fun chooseColor(isWin: Boolean): Int {
        val colorResId = if (isWin) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }
}