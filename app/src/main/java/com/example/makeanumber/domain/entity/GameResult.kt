package com.example.makeanumber.domain.entity

data class GameResult (
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countofQuestionsAnswered: Int,
    val gameSettings: GameSettings
        )