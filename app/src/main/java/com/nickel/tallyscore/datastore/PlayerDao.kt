package com.nickel.tallyscore.datastore

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nickel.tallyscore.data.Player
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
    suspend fun resetAllPlayerScores(emptyScores: List<Int> = listOf(0))

    @Query("SELECT * FROM player")
    fun getPlayers(): Flow<List<Player>>
}