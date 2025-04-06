package com.example.dailyhabittracker.data

import androidx.lifecycle.LiveData
import java.time.LocalDate

class HabitRepository(
    private val habitDao: HabitDao,
    private val habitCompletionDao: HabitCompletionDao
) {
    val allHabits: LiveData<List<Habit>> = habitDao.getAllHabits()

    suspend fun insertHabit(habit: Habit): Long {
        return habitDao.insert(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.delete(habit)
    }

    suspend fun getHabitById(id: Long): Habit? {
        return habitDao.getHabitById(id)
    }

    fun getCompletionsForHabit(habitId: Long): LiveData<List<HabitCompletion>> {
        return habitCompletionDao.getCompletionsForHabit(habitId)
    }

    fun getCompletionsForDate(date: LocalDate): LiveData<List<HabitCompletion>> {
        return habitCompletionDao.getCompletionsForDate(date)
    }

    suspend fun toggleHabitCompletion(habitId: Long, date: LocalDate) {
        val completion = habitCompletionDao.getCompletion(habitId, date)
        if (completion != null) {
            habitCompletionDao.deleteCompletion(habitId, date)
        } else {
            habitCompletionDao.insert(HabitCompletion(habitId, date))
        }
    }
}