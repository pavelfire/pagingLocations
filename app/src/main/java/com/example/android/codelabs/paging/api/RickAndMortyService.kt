/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val IN_QUALIFIER_RM = "in:name,description"

/**
 * Github API communication setup via Retrofit.
 */
interface RickAndMortyService {
    /**
     * Get repos ordered by stars.
     */
    @GET("location")
    suspend fun searchLocations(
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("dimension") dimension: String,
        @Query("page") page: Int,
    ): LocationsResponse

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        fun create(): RickAndMortyService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RickAndMortyService::class.java)
        }
    }
}
