package com.example.dailyhabittracker.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyhabittracker.data.Habit
import com.example.dailyhabittracker.data.HabitCompletion
import com.prs.myapplication.databinding.ItemHabitBinding

class HabitAdapter(
    private val onItemClick: (Habit) -> Unit,
    private val onCheckboxClick: (Habit, Boolean) -> Unit
) : ListAdapter<Habit, HabitAdapter.HabitViewHolder>(HabitDiffCallback()) {

    private var completions: List<HabitCompletion> = emptyList()

    fun setCompletions(completions: List<HabitCompletion>) {
        this.completions = completions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        holder.bind(habit)
    }
    
    // Add this method to ensure list updates are properly handled
    override fun submitList(list: List<Habit>?) {
        // Create a new list to force DiffUtil to run
        super.submitList(list?.let { ArrayList(it) })
    }

    inner class HabitViewHolder(private val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION) {
                   onItemClick(getItem(position))
               }
            }

            binding.checkBox.setOnClickListener {
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION) {
                   onCheckboxClick(getItem(position), binding.checkBox.isChecked)
               }
            }
        }

        fun bind(habit: Habit) {
            binding.habitTitle.text = habit.title
            binding.habitDescription.text = habit.description
            
            try {
                binding.colorIndicator.setBackgroundColor(Color.parseColor(habit.colorHex))
            } catch (e: IllegalArgumentException) {
                binding.colorIndicator.setBackgroundColor(Color.parseColor("#4CAF50"))
            }

            // Check if this habit is completed for the selected date
            val isCompleted = completions.any { it.habitId == habit.id }
            binding.checkBox.isChecked = isCompleted
        }
    }

    class HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }
}