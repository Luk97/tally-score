package com.nickel.tallyscore.datastore

import com.nickel.tallyscore.data.Player

class PlayerRepository(private val dao: PlayerDao) {

    val players = dao.getPlayers()

    suspend fun upsertPlayer(player: Player): Long = dao.upsertPlayer(player)

    suspend fun deletePlayer(player: Player) = dao.deletePlayer(player)

    suspend fun deleteAllPlayers() = dao.deleteAllPlayers()

    suspend fun resetAllPlayerScores() = dao.resetAllPlayerScores()
}