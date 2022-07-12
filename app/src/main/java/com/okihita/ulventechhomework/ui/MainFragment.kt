package com.okihita.ulventechhomework.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.okihita.ulventechhomework.R
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {


    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val monday = LocalDate.parse("2022-07-01")
        val friday = LocalDate.parse("2022-07-31")


        Log.d("Xena", "onCreate: viewmodel")
        viewModel.calculateBusinessDaysBetween(monday, friday)
    }

}