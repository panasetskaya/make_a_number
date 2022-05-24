package com.example.makeanumber.domain.useCases

import com.example.makeanumber.domain.entity.GameSettings
import com.example.makeanumber.domain.entity.Level
import com.example.makeanumber.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }


}