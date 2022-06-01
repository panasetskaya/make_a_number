package com.example.makeanumber.presentation

import GameRepositoryImpl
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeanumber.domain.entity.GameSettings
import com.example.makeanumber.domain.entity.Level
import com.example.makeanumber.domain.entity.Question
import com.example.makeanumber.domain.useCases.GenerateQuestionUseCase
import com.example.makeanumber.domain.useCases.GetGameSettingsUseCase

class GameViewModel : ViewModel() {

    private val repo = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repo)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repo)
    private lateinit var settings: GameSettings
    private lateinit var level: Level
    private val _questionLiveData = MutableLiveData<Question>()
    val questionLiveData: LiveData<Question>
        get() = _questionLiveData
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    fun startGame(thisLevel: Level) {
        setSettings(thisLevel)
        startTimer()

    }

    fun setSettings(thisLevel: Level) {
        this.level = thisLevel
        settings = getGameSettingsUseCase.invoke(thisLevel)
    }

    fun startTimer() {
        val timer = object : CountDownTimer(
            settings.gameTimeSeconds * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
    }

    fun getQuestion() {

    }

    private fun formatTime(p0: Long): String {
        val seconds = p0 / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MIN
        val leftSeconds = seconds - (minutes * SECONDS_IN_MIN)
        return String.format("%02d:%02d", minutes,leftSeconds)
    }

    private fun finishGame() {

    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MIN = 60
    }


}