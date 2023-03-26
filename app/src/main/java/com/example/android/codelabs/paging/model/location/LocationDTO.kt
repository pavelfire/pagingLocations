package com.example.android.codelabs.paging.model.location

data class LocationDTO(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)

fun LocationDTO.mapToEntity(locationDTO: LocationDTO): LocationEntity {
    return LocationEntity(
        created = created,
        dimension = dimension,
        id = id,
        name = name,
        residents = residents.joinToString(separator = "\n"),
        type = type,
        url = url
    )
}