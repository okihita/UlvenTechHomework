package com.okihita.ulventechhomework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.okihita.ulventechhomework.R
import com.okihita.ulventechhomework.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel>()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.businessDaysCount.observe(viewLifecycleOwner) {
            binding.tvBusinessDays.text = "Business Days: $it"
        }
        viewModel.holidaysBetweenDates.observe(viewLifecycleOwner) { holidays ->

            val stringBuilder = StringBuilder().append("List of Holidays\n")
            holidays.forEach {
                stringBuilder.append(it.localDate.dayOfWeek)
                    .append(", ").append(it.localDate)
                    .append(": ").append(it.description)
                    .append("\n")
            }

            binding.tvHolidays.text = stringBuilder.toString()
        }
    }

    private fun setupBindings() {

        // Setup date range picker and listener
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select start and end dates")
            .build()
        binding.btShowDatePicker.setOnClickListener {
            dateRangePicker.show(parentFragmentManager, "date picker")
        }
        dateRangePicker.addOnPositiveButtonClickListener {
            viewModel.calculateBusinessDaysBetween(
                Instant.ofEpochMilli(it.first).atZone(ZoneId.systemDefault()).toLocalDate(),
                Instant.ofEpochMilli(it.second).atZone(ZoneId.systemDefault()).toLocalDate()
            )
        }

        // Setup manual input fields and calculate button
        binding.btCalculate.setOnClickListener {
            try {
                val startDate = LocalDate.parse(binding.etStartDate.text)
                val endDate = LocalDate.parse(binding.etEndDate.text)
                viewModel.calculateBusinessDaysBetween(startDate, endDate)
            } catch (exception: DateTimeParseException) {
                exception.printStackTrace()
                Toast.makeText(activity, "Invalid dates. Make sure the start date and end date exists and formatted correctly.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}