package com.example.makeanumber.presentation

import GameRepositoryImpl
import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makeanumber.R
import com.example.makeanumber.domain.entity.GameResult
import com.example.makeanumber.domain.entity.GameSettings
import com.example.makeanumber.domain.entity.Level
import com.example.makeanumber.domain.entity.Question
import com.example.makeanumber.domain.useCases.GenerateQuestionUseCase
import com.example.makeanumber.domain.useCases.GetGameSettingsUseCase

class GameViewModel(
    application: Application,
    private val level: Level
) : ViewModel(){

    private var timer: CountDownTimer? = null
    private val context = application
    private val repo = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repo)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repo)
    private lateinit var settings: GameSettings
    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _questionLiveData = MutableLiveData<Question>()
    val questionLiveData: LiveData<Question>
        get() = _questionLiveData

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _answerProgress = MutableLiveData<String>()
    val answerProgress: LiveData<String>
        get() = _answerProgress

    private val _enoughAnswers = MutableLiveData<Boolean>()
    val enoughAnswers: LiveData<Boolean>
        get() = _enoughAnswers

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResultLiveData = MutableLiveData<GameResult>()
    val gameResultLiveData: LiveData<GameResult>
        get() = _gameResultLiveData

    init {
        startGame()
    }


    private fun startGame() {
        setSettings()
        startTimer()
        getQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        getQuestion()
    }

    private fun setSettings() {
        settings = getGameSettingsUseCase.invoke(level)
        _minPercent.value = settings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
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
        timer?.start()
    }

    private fun getQuestion() {
        _questionLiveData.value = generateQuestionUseCase.invoke(settings.maxSumValue)
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = questionLiveData.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val percent = calculateProgressPercent()
        _percentOfRightAnswers.value = percent
        _answerProgress.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            settings.minCountOfRightAnswers
        )
        _enoughAnswers.value = countOfRightAnswers >= settings.minCountOfRightAnswers
        _enoughPercent.value = percent >= settings.minPercentOfRightAnswers
    }

    private fun calculateProgressPercent(): Int {
        if (countOfQuestions==0) {
            return 0
        } else {
            return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun formatTime(p0: Long): String {
        val seconds = p0 / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MIN
        val leftSeconds = seconds - (minutes * SECONDS_IN_MIN)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        val gameResult = GameResult(
            win = enoughAnswers.value == true && enoughPercent.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countofQuestionsAnswered = countOfQuestions,
            gameSettings = settings
        )
        _gameResultLiveData.value = gameResult
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MIN = 60
    }


}