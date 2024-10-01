package com.nickel.tallyscore.datastore.playerdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nickel.tallyscore.data.Player

@Database(entities = [Player::class], version = 2)
@TypeConverters(PlayerTypeConverter::class)
abstract class PlayerDatabase: RoomDatabase() {
    abstract val dao: PlayerDao
}