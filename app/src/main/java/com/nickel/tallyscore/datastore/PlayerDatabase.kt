package com.nickel.tallyscore.datastore

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nickel.tallyscore.data.Player

@Database(entities = [Player::class], version = 1)
abstract class PlayerDatabase: RoomDatabase() {
    abstract val dao: PlayerDao
}