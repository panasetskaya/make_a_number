package com.example.makeanumber.domain.entity

data class GameSettings (
    val gameTimeSeconds: Int,
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int
        )