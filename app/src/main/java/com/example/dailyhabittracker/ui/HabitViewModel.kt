package com.example.dailyhabittracker.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dailyhabittracker.data.Habit
import kotlinx.coroutines.launch
import java.time.LocalDate

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    
    // Placeholder for database repository
    // private val repository: HabitRepository
    
    private val _allHabits = MutableLiveData<List<Habit>>(emptyList())
    val allHabits: LiveData<List<Habit>> = _allHabits
    
    init {
        // Initialize with sample data for now
        _allHabits.value = getSampleHabits()
    }
    
    fun getHabitById(habitId: Long): LiveData<Habit?> {
        val result = MutableLiveData<Habit?>()
        val habit = _allHabits.value?.find { it.id == habitId }
        result.value = habit
        return result
    }
    
    fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            // In a real app, you would insert into the database
            // repository.insertHabit(habit)
            
            // For now, just add to our list
            val currentList = _allHabits.value?.toMutableList() ?: mutableListOf()
            // Assign a temporary ID if needed
            val newHabit = if (habit.id == 0L) habit.copy(id = (currentList.maxOfOrNull { it.id } ?: 0) + 1) else habit
            currentList.add(newHabit)
            _allHabits.value = currentList
        }
    }
    
    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            // In a real app, you would update the database
            // repository.updateHabit(habit)
            
            // For now, just update our list
            val currentList = _allHabits.value?.toMutableList() ?: mutableListOf()
            val index = currentList.indexOfFirst { it.id == habit.id }
            if (index != -1) {
                currentList[index] = habit
                _allHabits.value = currentList
            }
        }
    }
    
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            // In a real app, you would delete from the database
            // repository.deleteHabit(habit)
            
            // For now, just remove from our list
            val currentList = _allHabits.value?.toMutableList() ?: mutableListOf()
            currentList.removeIf { it.id == habit.id }
            _allHabits.value = currentList
        }
    }
    
    fun toggleHabitCompletion(habitId: Long, date: LocalDate, isCompleted: Boolean) {
        viewModelScope.launch {
            // In a real app, you would update the completion status in the database
            // repository.setHabitCompletion(habitId, date, isCompleted)
            
            // For now, we'll just update the habit in our list
            val currentList = _allHabits.value?.toMutableList() ?: mutableListOf()
            val index = currentList.indexOfFirst { it.id == habitId }
            if (index != -1) {
                // This is a simplified approach - in a real app, you'd have a separate table for completions
                val habit = currentList[index]
                // We're not actually updating the habit here since we don't have a completions field
                // This is just a placeholder
            }
        }
    }
    
    // Sample data for testing
    private fun getSampleHabits(): List<Habit> {
        return listOf(
            Habit(
                id = 1,
                title = "Drink Water",
                description = "Drink 8 glasses of water daily",
                colorHex = "#2196F3",
                reminderEnabled = true,
                reminderTime = null
            ),
            Habit(
                id = 2,
                title = "Exercise",
                description = "30 minutes of exercise",
                colorHex = "#4CAF50",
                reminderEnabled = false,
                reminderTime = null
            ),
            Habit(
                id = 3,
                title = "Read",
                description = "Read for 20 minutes",
                colorHex = "#FF9800",
                reminderEnabled = true,
                reminderTime = null
            )
        )
    }
}