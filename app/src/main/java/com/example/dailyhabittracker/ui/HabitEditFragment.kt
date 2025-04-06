package com.example.dailyhabittracker.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dailyhabittracker.data.Habit
import com.prs.myapplication.databinding.FragmentHabitEditBinding
import java.time.LocalTime

class HabitEditFragment : Fragment() {

    private var _binding: FragmentHabitEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitViewModel by viewModels()
    // Change this line to use safeArgs instead of args
//    private val safeArgs by navArgs<HabitEditFragmentArgs>()
    private var selectedColor = "#4CAF50" // Default color
    private var currentHabit: Habit? = null

    // Define a list of colors for the color picker
    private val colors = listOf(
        "#F44336", // Red
        "#E91E63", // Pink
        "#9C27B0", // Purple
        "#673AB7", // Deep Purple
        "#3F51B5", // Indigo
        "#2196F3", // Blue
        "#03A9F4", // Light Blue
        "#00BCD4", // Cyan
        "#009688", // Teal
        "#4CAF50", // Green
        "#8BC34A", // Light Green
        "#CDDC39", // Lime
        "#FFEB3B", // Yellow
        "#FFC107", // Amber
        "#FF9800", // Orange
        "#FF5722"  // Deep Orange
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupColorPicker()
        setupReminderSwitch()
        setupSaveButton()
        setupDeleteButton()

        // If we're editing an existing habit, load its data
//        if (safeArgs.habitId != -1L) {
//            viewModel.getHabitById(safeArgs.habitId).observe(viewLifecycleOwner) { habit ->
//                if (habit != null) {
//                    currentHabit = habit
//                    populateUI(habit)
//                }
//            }
//        }
    }

    private fun setupColorPicker() {
        // Create color buttons dynamically
        for (color in colors) {
            val colorView = View(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(48, 48)
                setBackgroundColor(Color.parseColor(color))
                setPadding(8, 8, 8, 8)
                setOnClickListener {
                    selectedColor = color
                    updateSelectedColorIndicator()
                }
            }
            binding.colorContainer.addView(colorView)
        }
    }

    private fun updateSelectedColorIndicator() {
        // Update the UI to show which color is selected
        for (i in 0 until binding.colorContainer.childCount) {
            val child = binding.colorContainer.getChildAt(i)
            val layoutParams = child.layoutParams
            if (colors[i] == selectedColor) {
                layoutParams.width = 64
                layoutParams.height = 64
            } else {
                layoutParams.width = 48
                layoutParams.height = 48
            }
            child.layoutParams = layoutParams
        }
    }

    private fun setupReminderSwitch() {
        binding.reminderSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.timePickerLabel.visibility = if (isChecked) View.VISIBLE else View.GONE
            binding.timePicker.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            if (validateInput()) {
                saveHabit()
            }
        }
    }

    private fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            currentHabit?.let {
                viewModel.deleteHabit(it)
                findNavController().navigateUp()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateUI(habit: Habit) {
        binding.titleEditText.setText(habit.title)
        binding.descriptionEditText.setText(habit.description)
        selectedColor = habit.colorHex
        updateSelectedColorIndicator()

        binding.reminderSwitch.isChecked = habit.reminderEnabled
        binding.timePickerLabel.visibility = if (habit.reminderEnabled) View.VISIBLE else View.GONE
        binding.timePicker.visibility = if (habit.reminderEnabled) View.VISIBLE else View.GONE

        habit.reminderTime?.let { time ->
            binding.timePicker.hour = time.hour
            binding.timePicker.minute = time.minute
        }

        binding.deleteButton.visibility = View.VISIBLE
    }

    private fun validateInput(): Boolean {
        val title = binding.titleEditText.text.toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a habit title", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveHabit() {
        val title = binding.titleEditText.text.toString().trim()
        val description = binding.descriptionEditText.text.toString().trim()
        val reminderEnabled = binding.reminderSwitch.isChecked
        val reminderTime = if (reminderEnabled) {
            LocalTime.of(binding.timePicker.hour, binding.timePicker.minute)
        } else null

        val habit = if (currentHabit != null) {
            currentHabit!!.copy(
                title = title,
                description = description,
                colorHex = selectedColor,
                reminderEnabled = reminderEnabled,
                reminderTime = reminderTime
            )
        } else {
            Habit(
                title = title,
                description = description,
                colorHex = selectedColor,
                reminderEnabled = reminderEnabled,
                reminderTime = reminderTime
            )
        }

        if (currentHabit != null) {
            viewModel.updateHabit(habit)
        } else {
            viewModel.insertHabit(habit)
        }

        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}