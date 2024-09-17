package com.nickel.tallyscore.utils

import com.nickel.tallyscore.data.Player

object PlacementHelper {
    fun calculatePlacementOrder(players: List<Player>): List<Int> {
        val sortedPlayers = players.sortedByDescending { it.totalScore }
        val placements = mutableMapOf<Player, Int>()
        var currentRank = 1

        sortedPlayers.forEachIndexed() { index, player ->
            if (index > 0 && player.totalScore == sortedPlayers[index-1].totalScore ) {
                placements[player] = currentRank
            } else {
                currentRank = index + 1
                placements[player] = currentRank

            }
        }

        return players.map { placements[it] ?: 0 }
    }

    fun calculatePlayerPlacement(players: List<Player>): Int {
        return 1
    }
}