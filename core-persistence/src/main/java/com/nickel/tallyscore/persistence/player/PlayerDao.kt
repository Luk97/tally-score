package com.nickel.tallyscore.persistence.player

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Upsert
    suspend fun upsertPlayer(player: Player): Long

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("DELETE FROM player")
    suspend fun deleteAllPlayers()

    @Query("UPDATE player SET scores = :emptyScores")
    suspend fun resetAllPlayerScores(emptyScores: List<Int> = emptyList())

    @Query("SELECT * FROM player")
    fun getPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM player WHERE id = :playerId LIMIT 1")
    suspend fun getPlayerById(playerId: Long): Player?
}