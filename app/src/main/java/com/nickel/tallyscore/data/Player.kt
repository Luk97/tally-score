package com.nickel.tallyscore.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    val name: String,
    val score: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L
)
