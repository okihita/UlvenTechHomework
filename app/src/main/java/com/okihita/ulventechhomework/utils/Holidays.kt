package com.okihita.ulventechhomework.utils

import android.util.Log
import com.okihita.ulventechhomework.model.model.HolidayLocalDate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import kotlin.math.log

class Holidays {

    companion object {

        // Returns all holidays from January in year start to December in year end
        fun betweenYearInclusive(startYear: Int, endYear: Int): List<HolidayLocalDate> {

            val holidays = mutableListOf<HolidayLocalDate>()

            for (year in startYear..endYear) {

                // Criterion #1: Fixed Dates
                val anzacDay = LocalDate.of(year, Month.APRIL, 25)
                holidays.add(HolidayLocalDate(anzacDay, "Anzac Day"))

                val christmas = LocalDate.of(year, Month.DECEMBER, 25)
                holidays.add(HolidayLocalDate(christmas, "Christmas"))


                // Criterion #2: Same day each year as far as it is not a weekend

                // Add the New Year. If it falls on Saturday or Sunday, then make the following Monday
                // a public holiday to make up for the observation.
                val newYear = LocalDate.of(year, Month.JANUARY, 1)
                holidays.add(
                    when (newYear.dayOfWeek) {
                        DayOfWeek.SATURDAY -> HolidayLocalDate(newYear.plusDays(2), "New Year")
                        DayOfWeek.SUNDAY -> HolidayLocalDate(newYear.plusDays(1), "New Year")
                        else -> HolidayLocalDate(newYear, "New Year")
                    }
                )

                // Add the Labour Day. If it falls on Saturday or Sunday, then make the following Monday
                // a public holiday to make up for the observation.
                val labourDay = LocalDate.of(year, Month.MAY, 1)
                holidays.add(
                    when (labourDay.dayOfWeek) {
                        DayOfWeek.SATURDAY -> HolidayLocalDate(labourDay.plusDays(2), "Labour Day")
                        DayOfWeek.SUNDAY -> HolidayLocalDate(labourDay.plusDays(1), "Labour Day")
                        else -> HolidayLocalDate(labourDay, "Labour Day")
                    }
                )


                // Criterion #3: Certain occurrences on a certain day in a month

                // Add Queen's Birthday, every second Monday in June
                var queenBirthday = LocalDate.of(year, Month.JUNE, 8)
                while (queenBirthday.dayOfWeek != DayOfWeek.MONDAY) {
                    queenBirthday = queenBirthday.plusDays(1)
                }
                holidays.add(HolidayLocalDate(queenBirthday, "Queen's Birthday"))

            }

            return holidays
        }
    }
}