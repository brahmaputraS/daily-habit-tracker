package com.example.dailyhabittracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDate

@Dao
interface HabitCompletionDao {
    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId")
    fun getCompletionsForHabit(habitId: Long): LiveData<List<HabitCompletion>>

    @Query("SELECT * FROM habit_completions WHERE date = :date")
    fun getCompletionsForDate(date: LocalDate): LiveData<List<HabitCompletion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(completion: HabitCompletion)

    @Delete
    suspend fun delete(completion: HabitCompletion)

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId AND date = :date")
    suspend fun getCompletion(habitId: Long, date: LocalDate): HabitCompletion?

    @Query("DELETE FROM habit_completions WHERE habitId = :habitId AND date = :date")
    suspend fun deleteCompletion(habitId: Long, date: LocalDate)
}