package com.example.dailyhabittracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val reminderTime: LocalTime? = null,
    val reminderEnabled: Boolean = false,
    val colorHex: String = "#4CAF50" // Default color
)