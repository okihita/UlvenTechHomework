package com.okihita.ulventechhomework.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate

@RunWith(JUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `onJuly2022_return20`() {

        // Not including July 1st and July 31st
        val july1st = LocalDate.of(2022, 7, 1)
        val july31st = LocalDate.of(2022, 7, 31)
        viewModel.calculateBusinessDaysBetween(july1st, july31st)

        assertThat(viewModel.businessDaysCount.value).isEqualTo(20)
    }

    @Test
    fun `onJune2022_return29`() {

        val june1st = LocalDate.of(2022, 6, 1)
        val june30th = LocalDate.of(2022, 6, 30)

        viewModel.calculateBusinessDaysBetween(june1st, june30th)

        assertThat(viewModel.businessDaysCount.value).isEqualTo(19)
    }

    @Test
    fun `on2022AnzacDay_countMondayAsHoliday`() {

        val sun24apr2022 = LocalDate.of(2022, 4, 24)
        val sat30apr2022 = LocalDate.of(2022, 4, 30)

        viewModel.calculateBusinessDaysBetween(sun24apr2022, sat30apr2022)
        // Explanation:
        // Monday, 25 April is Anzac Day, public holiday
        // Tuesday to Friday are weekdays

        assertThat(viewModel.businessDaysCount.value).isEqualTo(4)
    }

    @Test
    fun `on2022NewYear_mark3JanPublicHoliday`() {

        val friDec31st2021 = LocalDate.of(2021, 12, 31)
        val satJan08th2022 = LocalDate.of(2022, 1, 8)

        viewModel.calculateBusinessDaysBetween(friDec31st2021, satJan08th2022)
        // Explanation:
        // JAN 1: Saturday (not counted)
        // JAN 2: Sunday (not counted)
        // JAN 3: Monday (not counted, because New Year is observed on this day)
        // JAN 4: Tuesday (counted)
        // JAN 5: Wednesday (counted)
        // JAN 6: Thursday (counted)
        // JAN 7: FRIDAY (counted)

        assertThat(viewModel.businessDaysCount.value).isEqualTo(4)
    }

    @Test
    fun `on2022QueenHoliday_countAsPublicHoliday`() {

        // Queen's Holiday is every second Monday in June
        // In 2022, it's June 13

        val tueMay31st2022 = LocalDate.of(2022, 5, 31)
        val sunJun19th2022 = LocalDate.of(2022, 6, 19)

        viewModel.calculateBusinessDaysBetween(tueMay31st2022, sunJun19th2022)
        // Explanation:
        // Wed-Fri, 01–03 June, 3 days
        // Mon–Fri, 06–10 June, 5 days
        // 13 June is a public holiday
        // Tue-Fri, 14–17 June, 4 days

        assertThat(viewModel.businessDaysCount.value).isEqualTo(12)
    }
}