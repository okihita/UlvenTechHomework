package com.okihita.ulventechhomework.model.remote

import com.okihita.ulventechhomework.model.model.HolidayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayApi {

    @GET("https://holidayapi.com/v1/holidays?pretty&key=fa6ae620-c14f-49ca-8846-ebfbe351fc37&country=SG")
    suspend fun getSingaporeHolidaysForYear(
        @Query("year") year: Int,
    ): HolidayResponse

}