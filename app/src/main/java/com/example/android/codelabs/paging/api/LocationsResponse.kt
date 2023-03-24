package com.example.android.codelabs.paging.api

import com.example.android.codelabs.paging.model.location.Info
import com.example.android.codelabs.paging.model.location.LocationDTO

data class LocationsResponse(
    val info: Info,
    val results: List<LocationDTO>
)