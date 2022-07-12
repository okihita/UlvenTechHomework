package com.okihita.ulventechhomework.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.okihita.ulventechhomework.model.model.HolidayLocalDate
import com.okihita.ulventechhomework.utils.Holidays
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.LongStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _businessDaysCount = MutableLiveData<Int>()
    var businessDaysCount: LiveData<Int> = _businessDaysCount

    private val _holidaysBetweenDates = MutableLiveData<List<HolidayLocalDate>>()
    var holidaysBetweenDates: LiveData<List<HolidayLocalDate>> = _holidaysBetweenDates

    fun calculateBusinessDaysBetween(startDate: LocalDate, endDate: LocalDate) {

        if (endDate < startDate || endDate == startDate) {
            return
        }

        // Between is inclusive-start and exclusive-end
        val daysBetween = ChronoUnit.DAYS.between(startDate.plusDays(1), endDate)

        // Returns a list of LocalDate between start and end (not including start and end)
        val localDates = LongStream
            .iterate(1) { i -> i + 1 }
            .limit(daysBetween)
            .mapToObj { i -> startDate.plusDays(i) }
            .collect(Collectors.toList())

        // STEP 1: Apply weekday filter
        val weekDays = localDates.filter {
            it.dayOfWeek != DayOfWeek.SATURDAY && it.dayOfWeek != DayOfWeek.SUNDAY
        }

        // STEP 2: Apply hard-coded holidays filter
        val holidaysBetweenYears = Holidays.betweenYearInclusive(startDate.year, endDate.year)
        val businessDays = weekDays.filter { weekday ->
            !holidaysBetweenYears.map { holidays -> holidays.localDate }.contains(weekday)
        }
        val holidaysBetweenDates = holidaysBetweenYears.filter { holidayLocalDate ->
            weekDays.contains(holidayLocalDate.localDate)
        }

        _businessDaysCount.value = businessDays.size // Called from main thread
        _holidaysBetweenDates.value = holidaysBetweenDates
    }
}