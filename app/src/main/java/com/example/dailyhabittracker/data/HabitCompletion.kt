package com.example.dailyhabittracker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.LocalDate

@Entity(
    tableName = "habit_completions",
    primaryKeys = ["habitId", "date"],
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = ["id"],
            childColumns = ["habitId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("habitId")]
)
data class HabitCompletion(
    val habitId: Long,
    val date: LocalDate,
    val completed: Boolean = true
)