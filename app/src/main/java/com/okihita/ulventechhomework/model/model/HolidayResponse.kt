package com.okihita.ulventechhomework.model.model

data class HolidayResponse(
    val status: Int,
    val warning: String,
    val holidays: List<Holiday>
)