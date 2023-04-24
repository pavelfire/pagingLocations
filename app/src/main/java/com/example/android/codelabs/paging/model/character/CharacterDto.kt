package com.example.android.codelabs.paging.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDto(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
): Parcelable

fun CharacterDto.mapToEntity(): CharacterEntity{
    return CharacterEntity(
        created = created,
        episode = episode.joinToString(separator = "\n"),
        gender = gender,
        id = id,
        image = image,
        location = "${location.name}\n${location.url}",
        name = name,
        origin = "${origin.name}\n${origin.url}",
        species = species,
        status = status,
        type = type,
        url = url
    )
}