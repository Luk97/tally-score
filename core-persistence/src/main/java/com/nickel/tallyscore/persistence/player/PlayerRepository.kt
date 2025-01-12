package com.nickel.tallyscore.persistence.player

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

    suspend fun addPlayerScore(player: Player, score: Int) {
        if (player.id != 0L) {
            dao.upsertPlayer(player.copy(scores = player.scores + score))
        }
    }

    suspend fun updatePlayerScore(player: Player, index: Int, score: Int) {
        if (player.id != 0L && index in player.scores.indices) {
            val scores = player.scores.toMutableList()
            scores[index] = score
            dao.upsertPlayer(player.copy(scores = scores))
        }
    }

    suspend fun updatePlayerList(players: List<Player>) {
        players.forEach {
            dao.upsertPlayer(it)
        }
    }

    suspend fun deletePlayerScore(player: Player, index: Int) {
        if (player.id != 0L) {
            val scores = player.scores.toMutableList()
            scores.removeAt(index)
            dao.upsertPlayer(player.copy(scores = scores))
        }
    }
}