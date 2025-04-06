package com.example.dailyhabittracker.ui
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.prs.myapplication.R
import com.prs.myapplication.databinding.FragmentHabitListBinding
import java.time.LocalDate

class HabitListFragment : Fragment() {

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitViewModel by viewModels()
    private lateinit var adapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupCalendarView()
        observeViewModel()

        // Set up FAB click listener
        requireActivity().findViewById<View>(R.id.fab)?.setOnClickListener {
            // Check if we're already in the edit fragment
            if (findNavController().currentDestination?.id == R.id.habitListFragment) {
                findNavController().navigate(R.id.action_habitListFragment_to_habitEditFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupRecyclerView() {
        adapter = HabitAdapter(
            onItemClick = { habit ->
                // Use the direct navigation with a bundle for the habitId
                val bundle = Bundle().apply {
                    putLong("habitId", habit.id)
                }
                findNavController().navigate(
                    R.id.action_habitListFragment_to_habitEditFragment,
                    bundle
                )
            },
            onCheckboxClick = { habit, isChecked ->
                viewModel.toggleHabitCompletion(habit.id, LocalDate.now(), isChecked)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    
    private fun setupCalendarView() {
        // Calendar view setup code would go here
        // This would depend on what calendar library you're using
    }
    
    private fun observeViewModel() {
        viewModel.allHabits.observe(viewLifecycleOwner) { habits ->
            adapter.submitList(habits)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}