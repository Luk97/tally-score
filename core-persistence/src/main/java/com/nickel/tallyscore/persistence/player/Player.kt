package com.nickel.tallyscore.persistence.player

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    val name: String,
    val scores: List<Int> = emptyList(),
    val placement: Int = 0,
    val missingTurns: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L
) {
    val turns: Int
        get() = scores.size

    val totalScore: Int
        get() = scores.sum()
}
