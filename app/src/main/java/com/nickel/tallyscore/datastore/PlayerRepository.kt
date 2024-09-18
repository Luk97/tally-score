package com.nickel.tallyscore.datastore

import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.utils.PlacementHelper
import kotlinx.coroutines.flow.map

class PlayerRepository(private val dao: PlayerDao) {

    val players = dao.getPlayers().map { players ->
        val placements = PlacementHelper.calculatePlacementOrder(players)
        val highestTurnCount = players.maxOfOrNull { it.turns } ?: 0
        players.mapIndexed { index, player ->
            player.copy(
                placement = placements[index],
                missingTurns = highestTurnCount - player.turns
            )
        }
    }

    suspend fun upsertPlayer(player: Player): Long = dao.upsertPlayer(player)

    suspend fun deletePlayer(player: Player) = dao.deletePlayer(player)

    suspend fun deleteAllPlayers() = dao.deleteAllPlayers()

    suspend fun resetAllPlayerScores() = dao.resetAllPlayerScores()

    suspend fun addPlayerScore(playerId: Long, score: Int) {
        val player = dao.getPlayerById(playerId)
        if (player != null) {
            dao.upsertPlayer(player.copy(scores = player.scores + score))
        }
    }

    suspend fun updatePlayerScore(playerId: Long, score: Int, index: Int) {
        val player = dao.getPlayerById(playerId)
        if (player != null && index in player.scores.indices) {
            val scores = player.scores.toMutableList()
            scores[index] = score
            dao.upsertPlayer(player.copy(scores = scores))
        }
    }

    suspend fun deletePlayerScore(playerId: Long, index: Int) {
        val player = dao.getPlayerById(playerId)
        if (player != null) {
            val scores = player.scores.toMutableList()
            scores.removeAt(index)
            dao.upsertPlayer(player.copy(scores = scores))
        }
    }
}