package com.nickel.tallyscore.preferences

data class UserPreferences(
    val boardSize: BoardSize = BoardSize.MEDIUM,
) {
    enum class BoardSize {
        SMALL,
        MEDIUM,
        LARGE
    }
}
