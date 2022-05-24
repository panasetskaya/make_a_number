package com.example.makeanumber.domain.repository

import com.example.makeanumber.domain.entity.GameSettings
import com.example.makeanumber.domain.entity.Level
import com.example.makeanumber.domain.entity.Question

interface GameRepository {

    fun getGameSettings(level: Level): GameSettings

    fun generateQuestion(
        maxSumValue: Int, countofOptions: Int): Question
}