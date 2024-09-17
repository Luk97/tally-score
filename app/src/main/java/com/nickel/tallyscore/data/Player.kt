package com.nickel.tallyscore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nickel.tallyscore.utils.PlacementHelper

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
