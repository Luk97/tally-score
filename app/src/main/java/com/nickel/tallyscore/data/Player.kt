package com.nickel.tallyscore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nickel.tallyscore.utils.PlacementHelper

@Entity
data class Player(
    val name: String,
    val scores: List<Int> = emptyList(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0L
) {
    val turns: Int
        get() = scores.size

    val totalScore: Int
        get() = scores.sum()

    fun getMissingTurns(turnCount: Int) = turnCount - turns

    fun getPlacement(players: List<Player>) = PlacementHelper.calculatePlayerPlacement(players)
}
