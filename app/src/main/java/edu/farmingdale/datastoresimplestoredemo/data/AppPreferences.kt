package edu.farmingdale.datastoresimplestoredemo.data

data class AppPreferences (
    val userName: String = "",
    val highScore: Int = 0,
    val darkMode: Boolean = false
)