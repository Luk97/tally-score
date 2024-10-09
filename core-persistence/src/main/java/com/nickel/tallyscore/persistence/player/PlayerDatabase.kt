package com.nickel.tallyscore.persistence.player

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Player::class], version = 2, exportSchema = false)
@TypeConverters(PlayerTypeConverter::class)
abstract class PlayerDatabase: RoomDatabase() {
    abstract val dao: PlayerDao
}