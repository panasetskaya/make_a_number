package com.example.makeanumber.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countofQuestionsAnswered: Int,
    val gameSettings: GameSettings
) : Parcelable